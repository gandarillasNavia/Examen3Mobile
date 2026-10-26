package org.ucb.appp1.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.notes.domain.model.NoteModel
import org.ucb.appp1.notes.domain.repository.NotesRepository

class ObserveNotesUseCase(private val repository: NotesRepository) {
    fun invoke(): Flow<List<NoteModel>> = repository.observe()
}
