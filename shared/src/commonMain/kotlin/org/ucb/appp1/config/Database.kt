package org.ucb.appp1.config

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import org.ucb.appp1.dollar.data.dao.DollarDao
import org.ucb.appp1.dollar.data.entity.DollarEntity
import org.ucb.appp1.settings.data.dao.ConfigItemDao
import org.ucb.appp1.settings.data.entity.ConfigItemEntity

@Database(entities = [DollarEntity::class, ConfigItemEntity::class], version = 2)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): DollarDao
    abstract fun getConfigDao(): ConfigItemDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}