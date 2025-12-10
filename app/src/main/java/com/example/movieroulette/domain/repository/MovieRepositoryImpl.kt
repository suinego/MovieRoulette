package com.example.movieroulette.domain.repository

import android.util.Log
import com.example.movieroulette.data.local.FavoriteMovie
import com.example.movieroulette.data.local.FavoriteMovieDao
import com.example.movieroulette.data.remote.dto.GenreDto
import com.example.movieroulette.data.remote.dto.MovieDto
import com.example.movieroulette.data.remote.TmdbApi
import com.example.movieroulette.domain.model.Genre
import com.example.movieroulette.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val api: TmdbApi,
    private val favoriteDao: FavoriteMovieDao
) : MovieRepository {

    private fun toDomain(dto: MovieDto): Movie {
        Log.d("MOVIE_REPO", "MovieDto id=${dto.id} title=${dto.title} posterPath=${dto.posterPath}")
        return Movie(
            id = dto.id,
            title = dto.title.orEmpty(),
            overview = dto.overview.orEmpty(),
            posterPath = dto.posterPath,
            voteAverage = dto.voteAverage ?: 0.0,
            releaseDate = dto.releaseDate
        )
    }


    private fun genreToDomain(dto: GenreDto) =
        Genre(id = dto.id, name = dto.name)

    override suspend fun getGenres(apiKey: String): List<Genre> {
        val resp = api.getGenres(apiKey)
        return resp.genres.map { genreToDomain(it) }
    }

    override suspend fun discoverMovies(apiKey: String, genresCsv: String?, page: Int): List<Movie> {
        val resp = api.discoverMovies(apiKey, genresCsv, page)
        return resp.results.map { toDomain(it)
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteDao.getAllFavorites().map { list ->
            list.map {
                Movie(
                    id = it.id,
                    title = it.title ?: "",
                    overview = it.overview ?: "",
                    posterPath = it.posterPath,
                    voteAverage = it.voteAverage ?: 0.0,
                    releaseDate = it.releaseDate
                )
            }
        }
    }

    override suspend fun addFavorite(movie: Movie) {
        favoriteDao.insert(
            FavoriteMovie(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                posterPath = movie.posterPath,
                voteAverage = movie.voteAverage,
                releaseDate = movie.releaseDate
            )
        )
    }

    override suspend fun removeFavorite(movie: Movie) {
        favoriteDao.delete(
            FavoriteMovie(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                posterPath = movie.posterPath,
                voteAverage = movie.voteAverage,
                releaseDate = movie.releaseDate
            )
        )
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return favoriteDao.isFavorite(id)
    }
}