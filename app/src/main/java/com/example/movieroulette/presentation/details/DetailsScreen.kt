package com.example.movieroulette.presentation.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieroulette.util.IMAGE_BASE
import org.koin.androidx.compose.koinViewModel
@Composable
fun DetailsScreen(
    movieId: Int?,
    title: String?,
    posterPath: String?,
    overview: String?,
    rating: Double?,
    releaseDate: String?
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        posterPath?.let {
            AsyncImage(
                model = "https://84.17.38.231/t/p/w500$it",
                contentDescription = title,
                modifier = Modifier.fillMaxWidth().height(420.dp)
            )
        }

        Spacer(Modifier.height(12.dp))
        Text(title ?: "")
        Spacer(Modifier.height(8.dp))
        Text("Рейтинг: ${rating ?: 0.0}")
        Text("Дата выхода: ${releaseDate ?: "—"}")
        Spacer(Modifier.height(12.dp))
        Text(overview ?: "")
    }
}
