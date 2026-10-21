package com.runly.auth.data.di

import com.runly.auth.data.AuthRepositoryImpl
import com.runly.auth.data.EmailPatternValidator
import com.runly.auth.domain.AuthRepository
import com.runly.auth.domain.PatternValidator
import com.runly.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> { EmailPatternValidator }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

}