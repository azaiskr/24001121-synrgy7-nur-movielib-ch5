package com.synrgy.mobielib.di

import android.content.Context
import com.synrgy.mobielib.data.SessionPreferences
import com.synrgy.mobielib.data.local.DatabaseImpl
import com.synrgy.mobielib.data.remote.api.ApiConfig
import com.synrgy.mobielib.repository.MovieLibRepo

object Injection {
    fun provideRepository(context: Context) : MovieLibRepo {
        val apiService = ApiConfig.getApiService(context)
        val sessionPreferences = SessionPreferences.getInstance(context)
        val databaseImpl = DatabaseImpl.getDatabase(context)
        return MovieLibRepo.getInstance(apiService,sessionPreferences,databaseImpl )
    }

}