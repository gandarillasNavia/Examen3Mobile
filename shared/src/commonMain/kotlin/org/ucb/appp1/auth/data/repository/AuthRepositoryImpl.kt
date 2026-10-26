package org.ucb.appp1.auth.data.repository

import org.ucb.appp1.auth.data.datasource.AuthRemoteDataSource
import org.ucb.appp1.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val dataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): Result<Unit> =
        runCatching { dataSource.signIn(email, password) }

    override suspend fun signUp(email: String, password: String): Result<Unit> =
        runCatching { dataSource.signUp(email, password) }

    override suspend fun signOut() = dataSource.signOut()

    override fun currentUserId(): String? = dataSource.currentUserId()
}
