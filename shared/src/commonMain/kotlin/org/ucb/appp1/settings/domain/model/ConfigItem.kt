package org.ucb.appp1.settings.domain.model

data class ConfigItem(
    val id: Int,
    val clave: String,
    val valor: String,
    val timestamp: Long
)
