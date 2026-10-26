package org.ucb.appp1.settings.data.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "config_items")
data class ConfigItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "clave")
    val clave: String,
    @ColumnInfo(name = "valor")
    val valor: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)
