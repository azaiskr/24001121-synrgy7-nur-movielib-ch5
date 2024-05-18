package com.synrgy.mobielib.data.remote.api

import com.synrgy.mobielib.data.remote.response.MovieDetailResponse
import com.synrgy.mobielib.data.remote.response.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getMovieListNowPlaying() : MovieListResponse

    @GET("movie/popular")
    suspend fun getMovieListPopular() : MovieListResponse

    @GET("movie/top_rated")
    suspend fun getMovieListTopRated() : MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id")
        movieId: Int
    ) : MovieDetailResponse

}