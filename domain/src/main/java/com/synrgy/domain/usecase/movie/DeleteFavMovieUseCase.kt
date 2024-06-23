package com.synrgy.domain.usecase.movie

import android.util.Log
import com.synrgy.common.Resource
import com.synrgy.domain.model.MovieListModel
import com.synrgy.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteFavMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun invoke(movieModel: MovieListModel)  {
        try{
            movieRepository.removeMovieFromFavorite(movieModel)
            Log.d("DeleteFavMovieUseCase", "invoke: success")
        }catch (e: Exception){
            e.printStackTrace()
            Log.d("DeleteFavMovieUseCase", "invoke: error ${e.message}")
        }
    }
}