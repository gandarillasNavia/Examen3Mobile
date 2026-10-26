package org.ucb.appp1.notes.presentation.viewmodel

sealed interface NotesEffect {
    data class ShowToast(val message: String) : NotesEffect
}
