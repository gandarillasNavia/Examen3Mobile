package org.ucb.appp1.exchange.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.exchange.presentation.viewmodel.ExchangeEvent
import org.ucb.appp1.exchange.presentation.viewmodel.ExchangeViewModel

@Composable
fun ExchangeScreen(
    navController: NavHostController,
    viewModel: ExchangeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.popBackStack() }) { Text("Volver") }
        Text("Valor en Realtime Database: ${state.message}")
        TextField(
            value = state.input,
            onValueChange = { viewModel.emitEvent(ExchangeEvent.OnInputChanged(it)) }
        )
        Button(onClick = { viewModel.emitEvent(ExchangeEvent.OnSend) }) { Text("Enviar") }
    }
}
