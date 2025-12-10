package com.example.movieroulette

import android.app.Application
import com.example.movieroulette.di.databaseModule
import com.example.movieroulette.di.networkModule
import com.example.movieroulette.di.repositoryModule
import com.example.movieroulette.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieRouletteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieRouletteApplication)
            modules(listOf(networkModule, databaseModule, repositoryModule, viewModelModule))
        }
    }
}
