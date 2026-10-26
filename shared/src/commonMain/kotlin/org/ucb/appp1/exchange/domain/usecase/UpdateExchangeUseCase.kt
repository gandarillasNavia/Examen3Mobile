package org.ucb.appp1.exchange.domain.usecase

import org.ucb.appp1.exchange.domain.repository.ExchangeRepository

class UpdateExchangeUseCase(private val repository: ExchangeRepository) {
    suspend fun invoke(value: String): Result<Unit> = repository.update(value)
}
