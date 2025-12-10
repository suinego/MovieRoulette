package com.example.movieroulette.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.squareup.moshi.Json

@Parcelize
data class Movie(
    val id: Int,
    val title: String,

    val overview: String,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "vote_average")
    val voteAverage: Double,

    @Json(name = "release_date")
    val releaseDate: String?
) : Parcelable
