package org.ucb.appp1.exchange.data.repository

import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.exchange.data.datasource.RealTimeDataBase
import org.ucb.appp1.exchange.domain.repository.ExchangeRepository

class ExchangeRepositoryImpl(
    private val realTimeDataBase: RealTimeDataBase
) : ExchangeRepository {
    override fun observe(): Flow<String?> = realTimeDataBase.observeMessage()

    override suspend fun update(value: String): Result<Unit> =
        runCatching { realTimeDataBase.setMessage(value) }
}
