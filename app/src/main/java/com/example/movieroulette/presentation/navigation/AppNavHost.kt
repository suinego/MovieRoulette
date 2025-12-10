package com.example.movieroulette.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.movieroulette.presentation.details.DetailsScreen
import com.example.movieroulette.presentation.favorites.FavoritesScreen
import com.example.movieroulette.presentation.genres.GenresScreen
import com.example.movieroulette.presentation.main.MainScreen
import com.example.movieroulette.presentation.navigation.Routes

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.GENRES) {
        composable(Routes.GENRES) { GenresScreen(navController) }

        composable(Routes.MAIN) { backStack ->
            val genresCsv = backStack.arguments?.getString("genres")
            MainScreen(navController)
        }

        composable(Routes.DETAILS) { backStack ->
            val savedState = navController.previousBackStackEntry?.savedStateHandle
            val movieId = savedState?.get<Int>("movieId")
            val title = savedState?.get<String>("movieTitle")
            val poster = savedState?.get<String>("moviePoster")
            val overview = savedState?.get<String>("movieOverview")
            val rating = savedState?.get<Double>("movieRating")
            val releaseDate = savedState?.get<String>("movieReleaseDate")

            DetailsScreen(
                movieId = movieId,
                title = title,
                posterPath = poster,
                overview = overview,
                rating = rating,
                releaseDate = releaseDate
            )
        }

        composable(Routes.FAVORITES) { FavoritesScreen(navController) }
    }
}
