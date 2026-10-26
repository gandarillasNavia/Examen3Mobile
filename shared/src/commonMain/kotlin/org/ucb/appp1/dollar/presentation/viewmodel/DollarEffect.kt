package org.ucb.appp1.dollar.presentation.viewmodel

sealed interface DollarEffect {
    data class ShowToast(val message: String) : DollarEffect
}
