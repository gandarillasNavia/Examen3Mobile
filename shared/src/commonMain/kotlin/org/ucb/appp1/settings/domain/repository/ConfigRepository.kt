package org.ucb.appp1.settings.domain.repository

import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.settings.domain.model.ConfigItem

interface ConfigRepository {
    fun observeAll(): Flow<List<ConfigItem>>
    suspend fun add(clave: String, valor: String)
    suspend fun delete(id: Int)
    suspend fun deleteAll()
    suspend fun seedIfEmpty()
}
