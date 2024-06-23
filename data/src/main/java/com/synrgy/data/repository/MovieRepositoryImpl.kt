package com.synrgy.data.repository

import android.util.Log
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getFavoriteMovies(): Flow<List<MovieListModel>> {
        return mMovieDao.getAllFavMovie().map { it.toMovieList() }
    }

    override suspend fun addMovieToFavorite(movie: MovieListModel) {
        mMovieDao.addFavMovie(movie.toMovieDataModel())
    }

    override suspend fun removeMovieFromFavorite(movie: MovieListModel) {
       mMovieDao.deleteFavMovie(movie.toMovieDataModel())
    }

}