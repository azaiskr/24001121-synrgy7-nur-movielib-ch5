package com.synrgy.domain.usecase.movie

import com.synrgy.common.Resource
import com.synrgy.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetListNowPlayingUseCase @Inject constructor(
    private val repo: MovieRepository
) {

    operator fun invoke () = flow {
        emit(Resource.Loading)
        try {
            val response = repo.getMovieListNowPlaying()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
     }

}