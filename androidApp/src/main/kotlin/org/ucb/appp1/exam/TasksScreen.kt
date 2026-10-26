package org.ucb.appp1.exam

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

private const val PREFS = "exam_prefs"
private const val KEY_MUTED = "muted"

@Composable
fun TasksScreen() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }
    var muted by remember { mutableStateOf(prefs.getBoolean(KEY_MUTED, false)) }
    var titles by remember { mutableStateOf(listOf<String>()) }
    var title by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    DisposableEffect(Unit) {
        var firstLoad = true
        val registration = FirebaseFirestore.getInstance()
            .collection("tareas")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    error = e.message
                    return@addSnapshotListener
                }
                if (snapshot == null) return@addSnapshotListener

                titles = snapshot.documents.map { it.getString("titulo") ?: "" }

                if (!firstLoad && !prefs.getBoolean(KEY_MUTED, false)) {
                    snapshot.documentChanges
                        .filter { it.type == DocumentChange.Type.ADDED }
                        .forEach { change ->
                            val name = change.document.getString("titulo") ?: ""
                            NotificationHelper.show(context, "Tareas", "Nueva tarea: $name")
                        }
                }
                firstLoad = false
            }
        onDispose { registration.remove() }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tareas (Firestore)")
        TextField(
            value = title,
            label = { Text("Titulo") },
            onValueChange = { title = it }
        )
        Button(onClick = {
            if (title.isNotBlank()) {
                FirebaseFirestore.getInstance().collection("tareas")
                    .add(
                        hashMapOf(
                            "titulo" to title,
                            "completada" to false,
                            "timestamp" to System.currentTimeMillis()
                        )
                    )
                    .addOnFailureListener { error = it.message }
                title = ""
            }
        }) { Text("Agregar") }

        Button(onClick = {
            muted = !muted
            prefs.edit().putBoolean(KEY_MUTED, muted).apply()
        }) { Text(if (muted) "Activar notificaciones" else "Silenciar notificaciones") }

        error?.let { Text("Error: $it") }
        LazyColumn {
            items(titles) { Text("• $it") }
        }
    }
}
