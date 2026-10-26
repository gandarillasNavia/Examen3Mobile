package org.ucb.appp1.dollar.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.dollar.presentation.viewmodel.DollarEvent
import org.ucb.appp1.dollar.presentation.viewmodel.DollarViewModel

@Composable
fun DollarScreen(
    navController: NavHostController,
    viewModel: DollarViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    Column {
        Button(onClick = { navController.popBackStack() }) { Text("Volver") }
        Button(onClick = { viewModel.emitEvent(DollarEvent.OnAddRecord) }) {
            Text("Add")
        }
        Text("Registros en Room: ${state.list.size}")
    }
}
