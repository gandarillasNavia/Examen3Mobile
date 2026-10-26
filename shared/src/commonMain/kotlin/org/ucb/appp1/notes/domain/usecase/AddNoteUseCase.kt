package org.ucb.appp1.notes.domain.usecase

import org.ucb.appp1.notes.domain.model.NoteModel
import org.ucb.appp1.notes.domain.repository.NotesRepository

class AddNoteUseCase(private val repository: NotesRepository) {
    suspend fun invoke(note: NoteModel): Result<Unit> {
        if (note.title.isBlank()) return Result.failure(IllegalArgumentException("Title is required"))
        return repository.add(note)
    }
}
