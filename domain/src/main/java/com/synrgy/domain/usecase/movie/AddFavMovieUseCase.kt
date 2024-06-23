package com.synrgy.domain.usecase.movie

import android.util.Log
import com.synrgy.common.Resource
import com.synrgy.domain.model.MovieListModel
import com.synrgy.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFavMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun invoke(movieModel: MovieListModel) {
        try{
            movieRepository.addMovieToFavorite(movieModel)
            Log.d("AddFavMovieUseCase", "invoke: success")
        }catch (e: Exception){
            e.printStackTrace()
            Log.d("AddFavMovieUseCase", "invoke: ${e.message}")
        }
    }
}