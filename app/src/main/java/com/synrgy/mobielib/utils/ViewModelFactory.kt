package com.synrgy.mobielib.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.synrgy.mobielib.di.Injection
import com.synrgy.mobielib.repository.MovieLibRepo
import com.synrgy.mobielib.ui.auth.AuthViewModel
import com.synrgy.mobielib.ui.main.DetailMovieViewModel
import com.synrgy.mobielib.ui.main.ListMovieViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (
    private val repository: MovieLibRepo
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ListMovieViewModel::class.java) -> {
                ListMovieViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }


    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstanceViewModel(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}