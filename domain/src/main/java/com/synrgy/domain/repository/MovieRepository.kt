package com.synrgy.domain.repository

import com.synrgy.domain.model.MovieDetailModel
import com.synrgy.domain.model.MovieListModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieListNowPlaying(): List<MovieListModel>
    suspend fun getMovieListPopular(): List<MovieListModel>
    suspend fun getMovieListTopRated(): List<MovieListModel>
    suspend fun getMovieDetail(movieId: Int): MovieDetailModel

    fun getFavoriteMovies(): Flow<List<MovieListModel>>
    suspend fun addMovieToFavorite(movie: MovieListModel)
    suspend fun removeMovieFromFavorite(movie: MovieListModel)

}