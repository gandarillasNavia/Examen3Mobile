package org.ucb.appp1.notes.presentation.viewmodel

sealed interface NotesEvent {
    data class OnTitleChanged(val value: String) : NotesEvent
    data class OnContentChanged(val value: String) : NotesEvent
    object OnSave : NotesEvent
}
