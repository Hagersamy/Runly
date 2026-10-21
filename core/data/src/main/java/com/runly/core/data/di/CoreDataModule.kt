package com.runly.core.data.di

import android.content.Context
import android.content.SharedPreferences
import com.runly.core.data.auth.EncryptedSessionStorage
import com.runly.core.data.network.HttpClientFactory
import com.runly.core.domain.auth.SessionStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences("auth_pref", Context.MODE_PRIVATE)
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

}
