package org.ucb.appp1.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.dollar.presentation.viewmodel.DollarViewModel
import org.ucb.appp1.exchange.presentation.viewmodel.ExchangeViewModel
import org.ucb.appp1.notes.presentation.viewmodel.NotesViewModel
import org.ucb.appp1.settings.presentation.viewmodel.ConfigViewModel
import org.ucb.appp1.signin.presentation.viewmodel.LoginViewModel
import org.ucb.appp1.userinformation.presentation.viewmodel.UserInformationViewModel

val presentationModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::UserInformationViewModel)
    viewModelOf(::DollarViewModel)
    viewModelOf(::ExchangeViewModel)
    viewModelOf(::NotesViewModel)
    viewModelOf(::ConfigViewModel)
}
