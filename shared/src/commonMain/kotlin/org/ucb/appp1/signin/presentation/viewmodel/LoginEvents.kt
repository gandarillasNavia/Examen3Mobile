package org.ucb.appp1.signin.presentation.viewmodel

sealed interface LoginEvents {
    object OnSubmit : LoginEvents
    object OnSubmitFirebase : LoginEvents
    data class OnEmailChanged(val value: String) : LoginEvents
    data class OnPasswordChanged(val value: String) : LoginEvents
}
