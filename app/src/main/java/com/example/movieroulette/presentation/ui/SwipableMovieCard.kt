package com.example.movieroulette.presentation.ui

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieroulette.domain.model.Movie
import com.example.movieroulette.util.IMAGE_BASE
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sign

@Composable
fun SwipableMovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onSwipeRight: (Movie) -> Unit,
    onSwipeLeft: (Movie) -> Unit,
    onTap: (Movie) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var rotation by remember { mutableStateOf(0f) }
    val animatedX by animateFloatAsState(targetValue = offsetX)
    val animatedY by animateFloatAsState(targetValue = offsetY)
    val animatedRotation by animateFloatAsState(targetValue = rotation)

    val scope = rememberCoroutineScope()
    val swipeThreshold = 200f

    Box(modifier = modifier) {
        if (animatedX > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAA00C853))
            )
        } else if (animatedX < 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAAd50000))
            )
        }

        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = animatedX
                    translationY = animatedY
                    rotationZ = animatedRotation
                }
                .pointerInput(movie.id) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                            rotation = (offsetX / 60f)
                        },
                        onDragEnd = {
                            if (abs(offsetX) > swipeThreshold) {
                                val dir = sign(offsetX)
                                scope.launch {
                                    offsetX = dir * 2000f
                                }
                                if (dir > 0) onSwipeRight(movie) else onSwipeLeft(movie)
                            } else {
                                scope.launch {
                                    offsetX = 0f
                                    offsetY = 0f
                                    rotation = 0f
                                }
                            }
                        }
                    )
                }
        ) {
            AsyncImage(
                model = if (movie.posterPath != null) IMAGE_BASE + movie.posterPath else null,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onTap(movie)
                            Log.d("IMAGE_TEST", "Tapped movie: ${movie.title}, URL: ${IMAGE_BASE + movie.posterPath}")
                        }
                    }
            )



            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .background(Color(0x99000000))
                        .padding(12.dp)
                ) {
                    Text(text = movie.title, color = Color.White)
                }
            }
        }
    }
}