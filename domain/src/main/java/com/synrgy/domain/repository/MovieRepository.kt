package com.synrgy.domain.repository

import com.synrgy.domain.model.MovieDetailModel
import com.synrgy.domain.model.MovieListModel
import com.synrgy.common.Resource

interface MovieRepository {
    suspend fun getMovieListNowPlaying(): List<MovieListModel>
    suspend fun getMovieListPopular(): List<MovieListModel>
    suspend fun getMovieListTopRated(): List<MovieListModel>
    suspend fun getMovieDetail(movieId: Int): MovieDetailModel
}