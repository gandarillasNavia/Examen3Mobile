package org.ucb.appp1.exchange.domain.repository

import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun observe(): Flow<String?>
    suspend fun update(value: String): Result<Unit>
}
