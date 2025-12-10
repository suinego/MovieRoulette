package com.example.movieroulette.di

import com.example.movieroulette.domain.repository.MovieRepositoryImpl
import com.example.movieroulette.domain.repository.MovieRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepositoryImpl(get(), get()) } bind MovieRepository::class
}
