package org.ucb.appp1.dollar.data.repository

import org.ucb.appp1.dollar.data.datasource.DollarLocalDataSource
import org.ucb.appp1.dollar.domain.model.DollarModel
import org.ucb.appp1.dollar.domain.repository.DollarRepository

class DollarRepositoryImpl(
    val localDataSource: DollarLocalDataSource
): DollarRepository {
    override suspend fun getList(): List<DollarModel> {
        return localDataSource.getList()
    }

    override suspend fun insert(dollar: DollarModel) {
        localDataSource.insert(dollar)
    }
}