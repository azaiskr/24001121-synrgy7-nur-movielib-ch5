package com.synrgy.data.local.movie

class FakeMovieDao : MovieDao {
    private val movies = mutableListOf<MovieDataModel>()

    override suspend fun addFavMovie(movie: MovieDataModel) {
        movies.add(movie)
    }

    override suspend fun deleteFavMovie(movie: MovieDataModel) {
        movies.remove(movie)
    }

    override suspend fun getAllFavMovie(): List<MovieDataModel> {
        return movies
    }
}