package com.example.movieroulette.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import  androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieroulette.presentation.navigation.Routes
import com.example.movieroulette.presentation.ui.SwipableMovieCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(navController: NavController, vm: MainViewModel = koinViewModel()) {
    val state by vm.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (state.movies.isEmpty()) {
            if (state.loading) {
                Text(
                    "Загрузка...",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Text(
                    "Нет фильмов",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            val stack = state.movies.take(3)
            stack.reversed().forEachIndexed { idx, movie ->
                val modifier = Modifier
                    .fillMaxSize()
                    .padding((idx * 8).dp)

                if (idx == stack.size - 1) {
                    SwipableMovieCard(
                        movie = movie,
                        modifier = modifier,
                        onSwipeLeft = { vm.popTop() },
                        onSwipeRight = {
                            vm.like(it)
                            vm.popTop()
                        },
                        onTap = {
                            navController.currentBackStackEntry?.savedStateHandle?.apply {
                                set("movieId", movie.id)
                                set("movieTitle", movie.title)
                                set("moviePoster", movie.posterPath)
                                set("movieOverview", movie.overview)
                                set("movieRating", movie.voteAverage)
                            }
                            navController.navigate(Routes.DETAILS)
                        }
                    )

                } else {
                    // Фоновая статичная карточка
                    Card(
                        modifier = modifier,
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        AsyncImage(
                            model = movie.posterPath?.let { posterPath ->
                                "https://image.tmdb.org/t/p/w500$posterPath"
                            },
                            contentDescription = movie.title,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate(Routes.FAVORITES) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Favorite, contentDescription = "Favorites")
        }
    }

    LaunchedEffect(state.movies.size) {
        if (!state.loading && state.movies.size < 6) {
            vm.loadNextPage(null)
        }
    }
}