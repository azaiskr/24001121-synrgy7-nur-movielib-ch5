package com.synrgy.domain.usecase.movie

import com.synrgy.common.Resource
import com.synrgy.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetDetailMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int) = flow {
                emit(Resource.Loading)
        try {
            val response = movieRepository.getMovieDetail(movieId)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occured"))
        }
    }
}