package org.ucb.appp1.signin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.auth.domain.usecase.SignInFirebaseUseCase
import org.ucb.appp1.signin.domain.model.Email
import org.ucb.appp1.signin.domain.model.Password
import org.ucb.appp1.signin.domain.usecase.AuthenticateUseCase

class LoginViewModel(
    private val authenticateUseCase: AuthenticateUseCase,
    private val signInFirebaseUseCase: SignInFirebaseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<LoginEffects>()
    val effects = _effects.asSharedFlow()

    private fun emitEffect(effect: LoginEffects) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    fun emitEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.OnEmailChanged -> _state.update { it.copy(email = event.value) }
            is LoginEvents.OnPasswordChanged -> _state.update { it.copy(password = event.value) }
            LoginEvents.OnSubmit -> submit(useFirebase = false)
            LoginEvents.OnSubmitFirebase -> submit(useFirebase = true)
        }
    }

    private fun submit(useFirebase: Boolean) {
        val current = _state.value
        if (current.email.isBlank()) {
            emitEffect(LoginEffects.ShowToast("The email field is required"))
            return
        }
        if (current.password.isBlank()) {
            emitEffect(LoginEffects.ShowToast("The password field is required"))
            return
        }
        viewModelScope.launch {
            val email = Email(current.email)
            val password = Password(current.password)
            val result = if (useFirebase) {
                signInFirebaseUseCase.invoke(email, password).map { true }
            } else {
                authenticateUseCase.invoke(email, password)
            }
            result.fold(
                onSuccess = { emitEffect(LoginEffects.NavigateToHome) },
                onFailure = { emitEffect(LoginEffects.ShowToast("Credential Invalid")) }
            )
        }
    }
}
