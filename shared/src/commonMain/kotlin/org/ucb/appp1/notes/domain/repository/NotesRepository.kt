package org.ucb.appp1.notes.domain.repository

import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.notes.domain.model.NoteModel

interface NotesRepository {
    fun observe(): Flow<List<NoteModel>>
    suspend fun add(note: NoteModel): Result<Unit>
}
