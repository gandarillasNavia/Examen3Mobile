package org.ucb.appp1.settings.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow
import org.ucb.appp1.settings.data.entity.ConfigItemEntity

@Dao
interface ConfigItemDao {
    @Query("SELECT * FROM config_items ORDER BY id DESC")
    fun getAll(): Flow<List<ConfigItemEntity>>

    @Insert
    suspend fun insert(item: ConfigItemEntity)

    @Query("DELETE FROM config_items WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM config_items")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM config_items")
    suspend fun count(): Int
}
