package org.ucb.appp1.exchange.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Realtime Database: nodo "app_dollar". Habilitar la base en la consola (modo de prueba). */
class RealTimeDataBase {

    private val database by lazy { Firebase.database }
    private val messageRef by lazy { database.reference("app_dollar") }

    fun observeMessage(): Flow<String?> {
        return messageRef.valueEvents
            .map { snapshot -> snapshot.value<String?>() }
    }

    suspend fun setMessage(value: String) {
        messageRef.setValue(value)
    }
}
