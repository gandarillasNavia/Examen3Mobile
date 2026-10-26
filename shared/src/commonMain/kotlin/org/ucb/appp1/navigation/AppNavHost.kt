package org.ucb.appp1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ucb.appp1.dollar.presentation.screen.DollarScreen
import org.ucb.appp1.exchange.presentation.screen.ExchangeScreen
import org.ucb.appp1.home.presentation.screen.HomeScreen
import org.ucb.appp1.notes.presentation.screen.NotesScreen
import org.ucb.appp1.settings.presentation.screen.ConfigScreen
import org.ucb.appp1.signin.presentation.screen.LoginScreen
import org.ucb.appp1.userinformation.presentation.screen.UserInformationScreen

/** PLANTILLA: composable<NavRoute.MiPantalla> { MiScreen(navController) } */
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Login) {
        composable<NavRoute.Login> { LoginScreen(navController) }
        composable<NavRoute.Home> { HomeScreen(navController) }
        composable<NavRoute.UserInformation> { UserInformationScreen(navController) }
        composable<NavRoute.Dollar> { DollarScreen(navController) }
        composable<NavRoute.Exchange> { ExchangeScreen(navController) }
        composable<NavRoute.Notes> { NotesScreen(navController) }
        composable<NavRoute.Config> { ConfigScreen() }
    }
}
