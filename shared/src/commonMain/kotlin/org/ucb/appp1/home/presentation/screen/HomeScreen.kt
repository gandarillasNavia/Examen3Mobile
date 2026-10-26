package org.ucb.appp1.home.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.ucb.appp1.navigation.NavRoute

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home")
        Button(onClick = { navController.navigate(NavRoute.UserInformation) }) { Text("Ktor (GitHub)") }
        Button(onClick = { navController.navigate(NavRoute.Dollar) }) { Text("Room") }
        Button(onClick = { navController.navigate(NavRoute.Exchange) }) { Text("Realtime Database") }
        Button(onClick = { navController.navigate(NavRoute.Notes) }) { Text("Firestore") }
        Button(onClick = { navController.navigate(NavRoute.Config) }) { Text("Room: configuraciones") }
    }
}
