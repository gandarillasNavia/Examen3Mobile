package org.ucb.appp1.userinformation.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.userinformation.presentation.viewmodel.UserInformationEffect
import org.ucb.appp1.userinformation.presentation.viewmodel.UserInformationEvent
import org.ucb.appp1.userinformation.presentation.viewmodel.UserInformationViewModel

@Composable
fun UserInformationScreen(
    navController: NavHostController,
    viewModel: UserInformationViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is UserInformationEffect.ShowToast -> println("INFO ${effect.message}")
                UserInformationEffect.NavigateToBack -> navController.popBackStack()
            }
        }
    }

    Column {
        Button(onClick = { viewModel.emitEvent(UserInformationEvent.OnBack) }) {
            Text("Volver")
        }
        TextField(value = state.value.alias, onValueChange = {
            viewModel.emitEvent(UserInformationEvent.OnAliasChange(it))
        })
        Button(onClick = {
            viewModel.emitEvent(UserInformationEvent.OnSubmit)
        }) {
            Text("Buscar")
        }
        state.value.email?.let {
            Text(it)
        }
        state.value.avatarUrl?.let {
            Text(it)
        }
    }
}