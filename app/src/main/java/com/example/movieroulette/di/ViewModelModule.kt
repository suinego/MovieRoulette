package com.example.movieroulette.di

import com.example.movieroulette.domain.usecase.*
import com.example.movieroulette.presentation.details.DetailsViewModel
import com.example.movieroulette.presentation.favorites.FavoritesViewModel
import com.example.movieroulette.presentation.genres.GenresViewModel
import com.example.movieroulette.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // Use cases
    single { GetGenresUseCase(get()) }
    single { DiscoverMoviesUseCase(get()) }
    single { GetFavoritesUseCase(get()) }
    single { AddFavoriteUseCase(get()) }
    single { RemoveFavoriteUseCase(get()) }
    single { IsFavoriteUseCase(get()) }

    // ViewModels
    viewModel { GenresViewModel(get()) }
    viewModel { MainViewModel(get(), get()) } // discover, addFavorite
    viewModel { DetailsViewModel(get(), get()) } // addFavorite, isFavorite
    viewModel { FavoritesViewModel(get(), get()) } // getFavorites, removeFavorite
}
