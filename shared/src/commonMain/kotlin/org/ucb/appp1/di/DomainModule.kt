package org.ucb.appp1.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.ucb.appp1.auth.domain.usecase.SignInFirebaseUseCase
import org.ucb.appp1.exchange.domain.usecase.ObserveExchangeUseCase
import org.ucb.appp1.exchange.domain.usecase.UpdateExchangeUseCase
import org.ucb.appp1.notes.domain.usecase.AddNoteUseCase
import org.ucb.appp1.notes.domain.usecase.ObserveNotesUseCase
import org.ucb.appp1.signin.domain.usecase.AuthenticateUseCase
import org.ucb.appp1.userinformation.domain.usecase.FindAliasUseCase

val domainModule = module {
    singleOf(::AuthenticateUseCase)
    singleOf(::SignInFirebaseUseCase)
    singleOf(::FindAliasUseCase)
    singleOf(::ObserveExchangeUseCase)
    singleOf(::UpdateExchangeUseCase)
    singleOf(::ObserveNotesUseCase)
    singleOf(::AddNoteUseCase)
}
