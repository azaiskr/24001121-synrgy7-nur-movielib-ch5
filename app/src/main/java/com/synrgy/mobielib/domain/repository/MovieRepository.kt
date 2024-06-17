package com.synrgy.mobielib.domain.repository

import com.synrgy.mobielib.data.remote.response.MovieDetailResponse
import com.synrgy.mobielib.data.remote.response.MovieListResponse

interface MovieRepository {
    suspend fun getMovieListNowPlaying(): MovieListResponse
    suspend fun getMovieListPopular(): MovieListResponse
    suspend fun getMovieListTopRated(): MovieListResponse
    suspend fun getMovieDetail(movieId: Int): MovieDetailResponse
}