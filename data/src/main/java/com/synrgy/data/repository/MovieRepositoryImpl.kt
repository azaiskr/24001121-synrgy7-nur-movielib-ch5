package com.synrgy.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.synrgy.common.Resource
import com.synrgy.data.SessionPreferences
import com.synrgy.data.local.DatabaseImpl
import com.synrgy.data.local.UserDao
import com.synrgy.data.remote.api.ApiService
import com.synrgy.data.local.UserDataModel
import com.synrgy.data.remote.response.toMovieDetail
import com.synrgy.data.remote.response.toMovieList
import com.synrgy.domain.model.MovieDetailModel
import com.synrgy.domain.model.MovieListModel
import com.synrgy.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : MovieRepository {
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


}