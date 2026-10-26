package org.ucb.appp1.navigation

import kotlinx.serialization.Serializable

/** PLANTILLA: una @Serializable object por pantalla (si lleva parametros, usar data class). */
@Serializable
sealed class NavRoute {
    @Serializable
    object Login : NavRoute()

    @Serializable
    object Home : NavRoute()

    @Serializable
    object UserInformation : NavRoute()

    @Serializable
    object Dollar : NavRoute()

    @Serializable
    object Exchange : NavRoute()

    @Serializable
    object Notes : NavRoute()

    @Serializable
    object Config : NavRoute()
}
