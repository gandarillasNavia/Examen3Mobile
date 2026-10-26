package org.ucb.appp1.notes.presentation.viewmodel

import org.ucb.appp1.notes.domain.model.NoteModel

data class NotesState(
    val title: String = "",
    val content: String = "",
    val notes: List<NoteModel> = emptyList(),
    val isLoading: Boolean = true
)
