package com.synrgy.mobielib.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.synrgy.mobielib.data.SessionPreferences
import com.synrgy.mobielib.data.local.DatabaseImpl
import com.synrgy.mobielib.data.local.UserDao
import com.synrgy.mobielib.data.local.UserModel
import com.synrgy.mobielib.data.remote.api.ApiService
import com.synrgy.mobielib.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MovieLibRepo private constructor(
    private val apiService: ApiService,
    private val pref: SessionPreferences,
    private val database: DatabaseImpl,
) {

    private val mUserDao: UserDao = database.userDao()

    fun register(user: UserModel) = liveData {
        try {
            val response = mUserDao.insert(user)
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message))
        }
    }

    fun login(email: String, password: String): LiveData<Response<UserModel>> = liveData {
        try {
            val response = mUserDao.getUser(email, password)
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message))
        }
    }


    fun getMovieListNowPlaying() = flow {
        emit(Response.Loading)
        try {
            val response = apiService.getMovieListNowPlaying()
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        }
    }

    fun getMovieListPopular() = flow {
        emit(Response.Loading)
        try {
            val response = apiService.getMovieListPopular()
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        }
    }

    fun getMovieListTopRated() = flow {
        emit(Response.Loading)
        try {
            val response = apiService.getMovieListTopRated()
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        }
    }

    fun getMovieDetail(movieId: Int) = flow {
        emit(Response.Loading)
        try {
            val response = apiService.getMovieDetail(movieId)
            Log.d("MovieLibRepo", "getMovieDetail: $response")
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unexpected error occured"))
        }
    }


    suspend fun saveSession(user: UserModel) = pref.saveSession(user)
    suspend fun clearSession() = pref.clearSession()
    fun getSession() : Flow<UserModel> = pref.getSession()

    companion object {
        @Volatile
        private var INSTANCE: MovieLibRepo? = null
        fun getInstance(apiService: ApiService, pref: SessionPreferences, database: DatabaseImpl): MovieLibRepo =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MovieLibRepo(apiService, pref, database).also { INSTANCE = it }
            }
    }
}