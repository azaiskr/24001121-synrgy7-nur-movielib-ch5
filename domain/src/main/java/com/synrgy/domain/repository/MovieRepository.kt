package com.synrgy.domain.repository

import com.synrgy.domain.model.MovieDetailModel
import com.synrgy.domain.model.MovieListModel

interface MovieRepository {
    suspend fun getMovieListNowPlaying(): List<MovieListModel>
    suspend fun getMovieListPopular(): List<MovieListModel>
    suspend fun getMovieListTopRated(): List<MovieListModel>
    suspend fun getMovieDetail(movieId: Int): MovieDetailModel

    suspend fun getFavoriteMovies(): List<MovieListModel>
    suspend fun addMovieToFavorite(movie: MovieListModel)
    suspend fun removeMovieFromFavorite(movie: MovieListModel)

}