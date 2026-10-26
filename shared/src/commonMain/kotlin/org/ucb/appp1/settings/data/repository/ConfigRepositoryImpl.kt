package org.ucb.appp1.settings.data.repository

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.ucb.appp1.settings.data.dao.ConfigItemDao
import org.ucb.appp1.settings.data.entity.ConfigItemEntity
import org.ucb.appp1.settings.domain.model.ConfigItem
import org.ucb.appp1.settings.domain.repository.ConfigRepository

@OptIn(ExperimentalTime::class)
class ConfigRepositoryImpl(
    private val dao: ConfigItemDao
) : ConfigRepository {

    private fun now(): Long = Clock.System.now().toEpochMilliseconds()

    override fun observeAll(): Flow<List<ConfigItem>> =
        dao.getAll().map { list -> list.map { it.toModel() } }

    override suspend fun add(clave: String, valor: String) {
        dao.insert(ConfigItemEntity(clave = clave, valor = valor, timestamp = now()))
    }

    override suspend fun delete(id: Int) = dao.deleteById(id)

    override suspend fun deleteAll() = dao.deleteAll()

    override suspend fun seedIfEmpty() {
        if (dao.count() == 0) {
            add("tema", "oscuro")
            add("idioma", "es")
            add("notificaciones", "activadas")
        }
    }

    private fun ConfigItemEntity.toModel() = ConfigItem(id, clave, valor, timestamp)
}
