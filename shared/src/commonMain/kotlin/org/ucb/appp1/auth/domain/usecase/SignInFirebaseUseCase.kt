package org.ucb.appp1.auth.domain.usecase

import org.ucb.appp1.auth.domain.repository.AuthRepository
import org.ucb.appp1.signin.domain.model.Email
import org.ucb.appp1.signin.domain.model.Password

class SignInFirebaseUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke(email: Email, password: Password): Result<Unit> =
        repository.signIn(email.value, password.value)
}
