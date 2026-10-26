package org.ucb.appp1.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.ucb.appp1.auth.data.datasource.AuthRemoteDataSource
import org.ucb.appp1.auth.data.repository.AuthRepositoryImpl
import org.ucb.appp1.auth.domain.repository.AuthRepository
import org.ucb.appp1.config.AppDatabase
import org.ucb.appp1.dollar.data.dao.DollarDao
import org.ucb.appp1.dollar.data.datasource.DollarLocalDataSource
import org.ucb.appp1.dollar.data.repository.DollarRepositoryImpl
import org.ucb.appp1.dollar.domain.repository.DollarRepository
import org.ucb.appp1.exchange.data.datasource.RealTimeDataBase
import org.ucb.appp1.exchange.data.repository.ExchangeRepositoryImpl
import org.ucb.appp1.exchange.domain.repository.ExchangeRepository
import org.ucb.appp1.notes.data.datasource.NotesRemoteDataSource
import org.ucb.appp1.notes.data.repository.NotesRepositoryImpl
import org.ucb.appp1.notes.domain.repository.NotesRepository
import org.ucb.appp1.settings.data.dao.ConfigItemDao
import org.ucb.appp1.settings.data.repository.ConfigRepositoryImpl
import org.ucb.appp1.settings.domain.repository.ConfigRepository
import org.ucb.appp1.userinformation.data.datasource.GithubRemoteDataSource
import org.ucb.appp1.userinformation.data.repository.GithubRepositoryImpl
import org.ucb.appp1.userinformation.data.service.GitHubApiService
import org.ucb.appp1.userinformation.domain.repository.GithubRepository

val dataModule = module {
    // Ktor
    single<GithubRemoteDataSource> { GitHubApiService() }
    single<GithubRepository> { GithubRepositoryImpl(get()) }

    // Room
    single<DollarDao> { get<AppDatabase>().getDao() }
    singleOf(::DollarLocalDataSource)
    single<DollarRepository> { DollarRepositoryImpl(get()) }

    // Room: (E25-2)
    single<ConfigItemDao> { get<AppDatabase>().getConfigDao() }
    single<ConfigRepository> { ConfigRepositoryImpl(get()) }

    // Realtime Database
    singleOf(::RealTimeDataBase)
    single<ExchangeRepository> { ExchangeRepositoryImpl(get()) }

    // Firestore
    singleOf(::NotesRemoteDataSource)
    single<NotesRepository> { NotesRepositoryImpl(get()) }

    // Firebase Authentication
    singleOf(::AuthRemoteDataSource)
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}
