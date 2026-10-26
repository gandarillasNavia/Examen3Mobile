package org.ucb.appp1.exchange.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.exchange.domain.repository.ExchangeRepository

class ObserveExchangeUseCase(private val repository: ExchangeRepository) {
    fun invoke(): Flow<String?> = repository.observe()
}
