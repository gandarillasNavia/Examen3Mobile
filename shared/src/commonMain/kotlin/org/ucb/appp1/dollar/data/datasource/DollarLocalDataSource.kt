package org.ucb.appp1.dollar.data.datasource

import org.ucb.appp1.dollar.data.dao.DollarDao
import org.ucb.appp1.dollar.data.entity.DollarEntity
import org.ucb.appp1.dollar.domain.model.DollarModel

class DollarLocalDataSource(
    val dao: DollarDao
) {
    suspend fun getList(): List<DollarModel> {
        return dao.getList().map {
            it.toModel()
        }
    }

    suspend fun insert(dollar: DollarModel) {
        dao.insert(dollar.toEntity())
    }

    private fun DollarEntity.toModel() : DollarModel {
        return DollarModel(
            official = dollarOfficial?: "",
            parallel = dollarParallel?:""
        )
    }

    private fun DollarModel.toEntity() = DollarEntity(
        dollarOfficial = official,
        dollarParallel = parallel
    )
}