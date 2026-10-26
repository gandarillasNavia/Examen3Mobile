package org.ucb.appp1.exchange.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.exchange.domain.usecase.ObserveExchangeUseCase
import org.ucb.appp1.exchange.domain.usecase.UpdateExchangeUseCase

class ExchangeViewModel(
    private val observeExchangeUseCase: ObserveExchangeUseCase,
    private val updateExchangeUseCase: UpdateExchangeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExchangeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeExchangeUseCase.invoke()
                .catch { error -> _state.update { it.copy(message = "Error: ${error.message}") } }
                .collect { value -> _state.update { it.copy(message = value ?: "Sin datos") } }
        }
    }

    fun emitEvent(event: ExchangeEvent) {
        when (event) {
            is ExchangeEvent.OnInputChanged -> _state.update { it.copy(input = event.value) }
            ExchangeEvent.OnSend -> viewModelScope.launch {
                updateExchangeUseCase.invoke(_state.value.input)
            }
        }
    }
}
