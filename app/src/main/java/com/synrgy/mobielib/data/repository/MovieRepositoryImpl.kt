package com.synrgy.mobielib.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.synrgy.mobielib.common.utils.Resource
import com.synrgy.mobielib.data.SessionPreferences
import com.synrgy.mobielib.data.local.DatabaseImpl
import com.synrgy.mobielib.data.local.UserDao
import com.synrgy.mobielib.data.local.UserModel
import com.synrgy.mobielib.data.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val pref: SessionPreferences,
    private val database: DatabaseImpl,
) {

    private val mUserDao: UserDao = database.userDao()

    fun register(user: UserModel) = liveData {
        try {
            val response = mUserDao.insert(user)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    fun login(email: String, password: String): LiveData<Resource<UserModel>> = liveData {
        try {
            val response = mUserDao.getUser(email, password)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }


    fun getMovieListNowPlaying() = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getMovieListNowPlaying()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
    }

    fun getMovieListPopular() = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getMovieListPopular()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
    }

    fun getMovieListTopRated() = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getMovieListTopRated()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
    }

    fun getMovieDetail(movieId: Int) = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getMovieDetail(movieId)
            Log.d("MovieLibRepo", "getMovieDetail: $response")
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
    }


    suspend fun saveSession(user: UserModel) = pref.saveSession(user)
    suspend fun clearSession() = pref.clearSession()
    fun getSession() : Flow<UserModel> = pref.getSession()

//    companion object {
//        @Volatile
//        private var INSTANCE: MovieRepositoryImpl? = null
//        fun getInstance(apiService: ApiService, pref: SessionPreferences, database: DatabaseImpl): MovieRepositoryImpl =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: MovieRepositoryImpl(apiService, pref, database).also { INSTANCE = it }
//            }
//    }
}