package com.synrgy.mobielib.di

import android.content.Context
import com.synrgy.mobielib.data.remote.api.ApiConfig
import com.synrgy.mobielib.repository.MovieLibRepo

object Injection {
    fun provideRepository(context: Context) : MovieLibRepo {
        val apiService = ApiConfig.getApiService(context)
        return MovieLibRepo.getInstance(apiService)
    }
}