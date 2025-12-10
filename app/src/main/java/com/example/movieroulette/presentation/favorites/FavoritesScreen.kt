package com.example.movieroulette.presentation.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavController

@Composable
fun FavoritesScreen(navController: NavController, vm: FavoritesViewModel = koinViewModel()) {
    val favs by vm.favorites.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(favs) { m ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set("movie", m)
                    navController.navigate("details")
                }
                .padding(8.dp)
            ) {
                AsyncImage(model = m.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }, contentDescription = m.title, modifier = Modifier.size(80.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(m.title)
                    Text("Рейтинг: ${m.voteAverage}")
                }
            }
        }
    }
}
