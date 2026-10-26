package org.ucb.appp1.dollar.presentation.viewmodel

sealed interface DollarEvent {
    object OnAddRecord: DollarEvent
}