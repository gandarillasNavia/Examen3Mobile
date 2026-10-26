package org.ucb.appp1.signin.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import appp1.shared.generated.resources.Res
import appp1.shared.generated.resources.login_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.navigation.NavRoute
import org.ucb.appp1.signin.presentation.viewmodel.LoginEffects
import org.ucb.appp1.signin.presentation.viewmodel.LoginEvents
import org.ucb.appp1.signin.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                LoginEffects.NavigateToHome -> navController.navigate(NavRoute.Home)
                is LoginEffects.ShowToast -> println("ERROR ${effect.message}")
                LoginEffects.SignUp -> Unit
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(Res.string.login_title))
        TextField(
            value = state.email,
            label = { Text("Email") },
            onValueChange = { viewModel.emitEvent(LoginEvents.OnEmailChanged(it)) }
        )
        TextField(
            value = state.password,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.emitEvent(LoginEvents.OnPasswordChanged(it)) }
        )
        Button(onClick = { viewModel.emitEvent(LoginEvents.OnSubmit) }) {
            Text("Login")
        }
        Button(onClick = { viewModel.emitEvent(LoginEvents.OnSubmitFirebase) }) {
            Text("Login con Firebase")
        }
    }
}
