package com.synrgy.data.repository

import com.synrgy.data.local.DatabaseImpl
import com.synrgy.data.local.movie.MovieDao
import com.synrgy.data.remote.api.ApiService
import com.synrgy.data.local.movie.toMovieDataModel
import com.synrgy.data.local.movie.toMovieList
import com.synrgy.data.remote.response.toMovieDetail
import com.synrgy.data.remote.response.toMovieList
import com.synrgy.domain.model.MovieDetailModel
import com.synrgy.domain.model.MovieListModel
import com.synrgy.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    databaseImpl: DatabaseImpl
) : MovieRepository {

    private val mMovieDao : MovieDao = databaseImpl.movieDao()


    override suspend fun getMovieListNowPlaying(): List<MovieListModel> {
        val response = apiService.getMovieListNowPlaying()
        return response.toMovieList()
    }

    override suspend fun getMovieListPopular(): List<MovieListModel> {
        val response = apiService.getMovieListPopular()
        return response.toMovieList()
    }

    override suspend fun getMovieListTopRated(): List<MovieListModel> {
        val response = apiService.getMovieListTopRated()
        return response.toMovieList()
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailModel {
        val response = apiService.getMovieDetail(movieId)
        return response.toMovieDetail()
    }

    override suspend fun getFavoriteMovies(): List<MovieListModel> {
        val response = mMovieDao.getAllFavMovie()
        return response.toMovieList()
    }

    override suspend fun addMovieToFavorite(movie: MovieListModel) {
        val response = mMovieDao.addFavMovie(movie.toMovieDataModel())
        return response
    }

    override suspend fun removeMovieFromFavorite(movie: MovieListModel) {
        val response = mMovieDao.deleteFavMovie(movie.toMovieDataModel())
        return response
    }
}