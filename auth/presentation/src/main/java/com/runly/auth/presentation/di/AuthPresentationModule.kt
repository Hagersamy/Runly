package com.runly.auth.presentation.di

import com.runly.auth.presentation.login.LoginViewModel
import com.runly.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}
