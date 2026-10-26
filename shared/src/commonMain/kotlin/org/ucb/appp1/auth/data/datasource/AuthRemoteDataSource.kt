package org.ucb.appp1.auth.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

/** Firebase Authentication (correo y contrasena). Habilitar el proveedor en la consola de Firebase. */
class AuthRemoteDataSource {
    private val auth by lazy { Firebase.auth }

    suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }

    suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
    }

    suspend fun signOut() {
        auth.signOut()
    }

    fun currentUserId(): String? = auth.currentUser?.uid
}
