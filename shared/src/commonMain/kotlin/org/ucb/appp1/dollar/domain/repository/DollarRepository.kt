package org.ucb.appp1.dollar.domain.repository

import org.ucb.appp1.dollar.domain.model.DollarModel

interface DollarRepository {
    suspend fun getList(): List<DollarModel>

    suspend fun insert(dollar: DollarModel)
}