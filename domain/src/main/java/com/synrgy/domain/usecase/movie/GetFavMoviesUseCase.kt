package com.synrgy.domain.usecase.movie

import com.synrgy.domain.model.MovieListModel
import com.synrgy.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(): Flow<List<MovieListModel>> = movieRepository.getFavoriteMovies()
}