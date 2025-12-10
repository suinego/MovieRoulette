package com.example.movieroulette.presentation.genres

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieroulette.presentation.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun GenresScreen(navController: NavController, vm: GenresViewModel = koinViewModel()) {
    val state by vm.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Выберите жанры", modifier = Modifier.padding(8.dp))
        if (state.loading) {
            Text("Загрузка...")
        } else if (state.error != null) {
            Text("Ошибка: ${state.error}")
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
                items(state.genres) { genre ->
                    val selected = state.selectedIds.contains(genre.id)
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = if (selected) androidx.compose.ui.graphics.Color(0xFF1976D2) else androidx.compose.ui.graphics.Color(0xFFEEEEEE),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        )
                        .clickable { vm.toggleSelection(genre.id) }
                        .padding(12.dp)
                    ) {
                        Text(text = genre.name, color = if (selected) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.Black)
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { vm.clear() }) { Text("Очистить") }
                Button(onClick = {
                    val csv = state.selectedIds.joinToString(",")
                    navController.navigate(Routes.MAIN) {
                    }
                    navController.currentBackStackEntry?.savedStateHandle?.set("genres", csv)
                }) { Text("Применить") }
            }
        }
    }
}
