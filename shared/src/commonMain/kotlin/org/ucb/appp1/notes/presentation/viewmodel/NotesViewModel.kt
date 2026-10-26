package org.ucb.appp1.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.notes.domain.model.NoteModel
import org.ucb.appp1.notes.domain.usecase.AddNoteUseCase
import org.ucb.appp1.notes.domain.usecase.ObserveNotesUseCase

class NotesViewModel(
    private val observeNotesUseCase: ObserveNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<NotesEffect>()
    val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            observeNotesUseCase.invoke()
                .catch { error ->
                    _state.update { it.copy(isLoading = false) }
                    _effect.emit(NotesEffect.ShowToast(error.message ?: "Error"))
                }
                .collect { list -> _state.update { it.copy(notes = list, isLoading = false) } }
        }
    }

    fun emitEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.OnTitleChanged -> _state.update { it.copy(title = event.value) }
            is NotesEvent.OnContentChanged -> _state.update { it.copy(content = event.value) }
            NotesEvent.OnSave -> viewModelScope.launch {
                val current = _state.value
                addNoteUseCase.invoke(NoteModel(current.title, current.content)).fold(
                    onSuccess = { _state.update { it.copy(title = "", content = "") } },
                    onFailure = { _effect.emit(NotesEffect.ShowToast(it.message ?: "Error")) }
                )
            }
        }
    }
}
