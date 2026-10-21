package com.example.runly

import android.app.Application
import com.runly.auth.data.di.authDataModule
import com.runly.auth.presentation.di.authPresentationModule
import com.runly.core.data.di.coreDataModule
import com.runly.run.location.di.locationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import timber.log.Timber

class RunlyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@RunlyApp)
            modules(
                appModule,
                authDataModule,
                authPresentationModule,
                coreDataModule,
                locationModule
            )
        }
    }
}

val appModule = module {
    viewModelOf(::MainViewModel)
}
