import dev.detekt.gradle.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipInputStream

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room3)
}

detekt {
    source.setFrom(
        "src/commonMain/kotlin",
        "src/androidMain/kotlin",
        "src/iosMain/kotlin"
    )
    config.setFrom(files("${rootDir}/config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    ignoreFailures = false
}

tasks.withType<Detekt>().configureEach {
    include("**/*.kt", "**/*.kts")
    exclude("**/build/**")
}

tasks.register<Detekt>("detektAll") {
    description = "Analiza todo el código fuente KMP"
    group = "verification"
    setSource(files("src"))
    include("**/*.kt", "**/*.kts")
    exclude("**/build/**")

    reports {
        html.required.set(true)
        sarif.required.set(true)
    }
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    android {
        namespace = "org.ucb.appp1.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            // Navegacion y serializacion
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            // Ktor (el motor va en androidMain / iosMain)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            // Room 3
            implementation(libs.androidx.room3.runtime)
            implementation(libs.androidx.sqlite.bundled)
            // Firebase (GitLive)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.database)
            implementation(libs.firebase.firestore)
        }
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.room3.sqlite.wrapper)
            implementation(libs.androidx.work.runtime.ktx)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
    add("kspAndroid", libs.androidx.room3.compiler)
    // KSP de Room para iOS: solo si la configuracion existe en este equipo
    listOf("kspIosArm64", "kspIosSimulatorArm64").forEach { configName ->
        if (configurations.names.contains(configName)) {
            add(configName, libs.androidx.room3.compiler)
        }
    }
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

// ---------------------------------------------------------------------------
// Localise.biz: ./gradlew :shared:downloadLocaliseTranslations
// Requiere LOCO_API_KEY en ~/.gradle/gradle.properties (NO en el repositorio)
// ---------------------------------------------------------------------------
val locoApiKey = providers.gradleProperty("LOCO_API_KEY").orElse("")
tasks.register("downloadLocaliseTranslations") {
    group = "localization"
    description = "Descarga las traducciones desde Loco y las ubica en composeResources"

    notCompatibleWithConfigurationCache("Descarga externa desde la API de Loco")

    doLast {
        val apiKey = locoApiKey.get()

        if (apiKey.isBlank()) {
            throw GradleException("Configura LOCO_API_KEY en ~/.gradle/gradle.properties")
        }

        println("Iniciando descarga de traducciones desde Loco (localise.biz)...")

        val url = URL("https://localise.biz/api/export/archive/xml.zip?key=$apiKey")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
        }

        val responseCode = connection.responseCode
        if (responseCode == 200) {
            val outputZip = layout.buildDirectory.file("translations.zip").get().asFile
            val tempExtractDir = layout.buildDirectory.dir("temp_translations").get().asFile
            val targetDir = projectDir.resolve("src/commonMain/composeResources")

            outputZip.parentFile.mkdirs()
            connection.inputStream.use { input ->
                outputZip.outputStream().use { output -> input.copyTo(output) }
            }

            if (tempExtractDir.exists()) tempExtractDir.deleteRecursively()
            tempExtractDir.mkdirs()

            ZipInputStream(outputZip.inputStream()).use { zis ->
                var entry = zis.nextEntry
                while (entry != null) {
                    val newFile = File(tempExtractDir, entry.name)
                    if (entry.isDirectory) {
                        newFile.mkdirs()
                    } else {
                        newFile.parentFile?.mkdirs()
                        newFile.outputStream().use { fos -> zis.copyTo(fos) }
                    }
                    zis.closeEntry()
                    entry = zis.nextEntry
                }
            }

            val foundValuesDir = tempExtractDir.walkTopDown()
                .find { it.isDirectory && it.name.startsWith("values") }?.parentFile
                ?: tempExtractDir

            foundValuesDir.listFiles()?.forEach { file ->
                if (file.isDirectory && file.name.startsWith("values")) {
                    val destination = File(targetDir, file.name)
                    destination.deleteRecursively()
                    file.copyRecursively(destination, overwrite = true)
                    println("Copiado idioma: ${file.name} -> ${destination.absolutePath}")
                }
            }

            tempExtractDir.deleteRecursively()
            println("Traducciones organizadas en ${targetDir.absolutePath}")
        } else {
            val errorResponse = connection.errorStream?.bufferedReader()?.readText() ?: "Error desconocido"
            throw GradleException("Error al conectar con Loco ($responseCode): $errorResponse")
        }
    }
}
