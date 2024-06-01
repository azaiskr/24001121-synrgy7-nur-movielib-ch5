package com.synrgy.mobielib.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.mobielib.data.remote.response.MovieListResponse
import com.synrgy.mobielib.repository.MovieLibRepo
import com.synrgy.mobielib.utils.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListMovieViewModel(private val repo: MovieLibRepo) : ViewModel() {
    private val _nowPlayingMovies = MutableStateFlow<Response<MovieListResponse>>(Response.Loading)
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow<Response<MovieListResponse>>(Response.Loading)
    val popularMovies = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<Response<MovieListResponse>>(Response.Loading)
    val topRatedMovies = _topRatedMovies.asStateFlow()

    fun getMovieListNowPlaying() {
        viewModelScope.launch {
            repo.getMovieListNowPlaying()
                .collect {
                    when (it) {
                        is Response.Loading -> {
                            _nowPlayingMovies.value = Response.Loading
                        }

                        is Response.Success -> {
                            _nowPlayingMovies.value = Response.Success(it.data)
                        }

                        is Response.Error -> {
                            _nowPlayingMovies.value = Response.Error(it.exception)
                        }
                    }
                }
        }
    }

    fun getMovieListPopular() {
        viewModelScope.launch {
            repo.getMovieListPopular()
                .collect {
                    when (it) {
                        is Response.Loading -> {
                            _popularMovies.value = Response.Loading
                        }

                        is Response.Success -> {
                            _popularMovies.value = Response.Success(it.data)
                        }

                        is Response.Error -> {
                            _popularMovies.value = Response.Error(it.exception)
                        }
                    }
                }
        }
    }


    fun getMovieListTopRated() {
        viewModelScope.launch {
            repo.getMovieListTopRated()
                .collect {
                    when (it) {
                        is Response.Loading -> {
                            _topRatedMovies.value = Response.Loading
                        }

                        is Response.Success -> {
                            _topRatedMovies.value = Response.Success(it.data)
                        }

                        is Response.Error -> {
                            _topRatedMovies.value = Response.Error(it.exception)
                        }
                    }
                }
        }
    }
}

