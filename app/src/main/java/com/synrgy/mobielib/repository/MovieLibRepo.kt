package com.synrgy.mobielib.repository

import com.synrgy.mobielib.data.remote.api.ApiService
import com.synrgy.mobielib.utils.Response
import kotlinx.coroutines.flow.flow

class MovieLibRepo private constructor(
    private val apiService: ApiService,
) {

    fun getMovieListNowPlaying() = flow {
        emit(Response.Loading)
        try {
            val response = apiService.getMovieListNowPlaying()
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        }
    }

    fun getMovieListPopular() = flow {
        emit(Response.Loading)
        try {
            val response = apiService.getMovieListPopular()
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        }
    }

    fun getMovieListTopRated() = flow {
        emit(Response.Loading)
        try {
            val response = apiService.getMovieListTopRated()
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        }
    }

    fun getMovieDetail(movieId: Int) = flow {
        emit(Response.Loading)
        try {
            val response = apiService.getMovieDetail(movieId)
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MovieLibRepo? = null
        fun getInstance(apiService: ApiService): MovieLibRepo =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MovieLibRepo(apiService).also { INSTANCE = it }
            }
    }
}