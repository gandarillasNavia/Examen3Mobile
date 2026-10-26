package org.ucb.appp1.exchange.presentation.viewmodel

sealed interface ExchangeEvent {
    data class OnInputChanged(val value: String) : ExchangeEvent
    object OnSend : ExchangeEvent
}
