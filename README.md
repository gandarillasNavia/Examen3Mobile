# Examen de Desarrollo Móvil — Entrega

## Integrantes
- Mateo S. Gandarillas Navia

## Combinación elegida (100 puntos)
| Ejercicio | Puntos | Servicios |
|---|---|---|
| E25-2 · Room como fuente de verdad para configuración local | 25 | Room |
| E25-4 · Realtime Database: contador global en tiempo real | 25 | Realtime Database |
| E50-5 · Firestore + notificación interna al agregar documento | 50 | Firestore + NotificationManager + SharedPreferences |

## Instrucciones
1. Abrir el proyecto en Android Studio y sincronizar Gradle.
2. Crear un proyecto en Firebase, registrar la app Android con el paquete `org.ucb.appp1` y colocar su `google-services.json` en `androidApp/`.
3. En la consola de Firebase: crear **Realtime Database** y **Firestore** en modo de prueba.
4. Ejecutar `./gradlew :androidApp:assembleDebug` o correr desde Android Studio.
5. La app abre con una barra inferior: **Room**, **Contador**, **Tareas**.

## Cómo verificar cada ejercicio
- **E25-2 (pestaña Room):** al primer arranque aparecen 3 registros sembrados. Agregar, borrar uno y «Borrar todo»: la lista cambia sola (Flow). Cerrar la app y reabrir: los datos persisten.
- **E25-4 (pestaña Contador):** pulsar «+1»; en la consola de Firebase, el nodo `contador/valor` sube. Cambiar el valor desde la consola: la app muestra el nuevo valor y un Toast.
- **E50-5 (pestaña Tareas):** aceptar el permiso de notificaciones. Agregar una tarea (o crear un documento en la colección `tareas` desde la consola con `titulo`, `completada`, `timestamp` numérico): aparece la notificación «Nueva tarea: X». Tocarla abre la pestaña Tareas. «Silenciar notificaciones» persiste tras reiniciar.

## Decisiones técnicas
- **Arquitectura:** módulo `shared` (Kotlin Multiplatform + Compose Multiplatform) con capas Data, Domain y Presentation e inyección con Koin. Room vive en `shared`.
- **E25-4 y E50-5** usan el SDK nativo de Firebase para Android (`addSnapshotListener`, `ValueEventListener`, `runTransaction`) en el módulo `androidApp`, por ser APIs de plataforma (notificaciones, permisos, `PendingIntent`).
- **Room:** `Flow` en el DAO; la UI se actualiza sola al cambiar la tabla. Siembra de 3 registros solo si la tabla está vacía.
- **Contador:** `runTransaction` evita condiciones de carrera entre dispositivos; persistencia offline activada en `MainApplication`.
- **Notificaciones:** canal creado al iniciar, permiso `POST_NOTIFICATIONS` pedido en Android 13+, `PendingIntent` con extra que abre la pestaña de tareas. La primera carga del listener no notifica (solo documentos añadidos después).
