package com.synrgy.data.local.movie

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeMovieDao : MovieDao {
    private val movies = mutableListOf<MovieDataModel>()
    private val moviesFlow = MutableStateFlow<List<MovieDataModel>>(emptyList())


    override suspend fun addFavMovie(movie: MovieDataModel) {
        movies.add(movie)
        moviesFlow.value = movies.toList()
    }

    override suspend fun deleteFavMovie(movie: MovieDataModel) {
        movies.remove(movie)
        moviesFlow.value = movies.toList()
    }

    override fun getAllFavMovie(): Flow<List<MovieDataModel>> {
        return moviesFlow.asStateFlow()

    }
}