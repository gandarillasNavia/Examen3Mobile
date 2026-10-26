package org.ucb.appp1.notes.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.ucb.appp1.notes.data.dto.NoteDto

/** Firestore: coleccion "notes". Habilitar Firestore en la consola (modo de prueba). */
class NotesRemoteDataSource {
    private val collection by lazy { Firebase.firestore.collection("notes") }

    fun observe(): Flow<List<NoteDto>> =
        collection.snapshots.map { snapshot ->
            snapshot.documents.map { it.data<NoteDto>() }
        }

    suspend fun add(note: NoteDto) {
        collection.add(note)
    }
}
