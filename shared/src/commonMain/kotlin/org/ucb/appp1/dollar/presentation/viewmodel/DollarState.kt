package org.ucb.appp1.dollar.presentation.viewmodel

import org.ucb.appp1.dollar.domain.model.DollarModel

data class DollarState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val list: List<DollarModel> = emptyList()
)
