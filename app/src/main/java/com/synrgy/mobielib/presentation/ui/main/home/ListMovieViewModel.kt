package com.synrgy.mobielib.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.mobielib.common.utils.Resource
import com.synrgy.mobielib.data.remote.response.MovieListResponse
import com.synrgy.mobielib.data.repository.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(
    private val repo: MovieRepositoryImpl,
) : ViewModel() {
    private val _nowPlayingMovies = MutableStateFlow<Resource<MovieListResponse>>(Resource.Loading)
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow<Resource<MovieListResponse>>(Resource.Loading)
    val popularMovies = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<Resource<MovieListResponse>>(Resource.Loading)
    val topRatedMovies = _topRatedMovies.asStateFlow()

    fun getMovieListNowPlaying() {
        viewModelScope.launch {
            repo.getMovieListNowPlaying()
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _nowPlayingMovies.value = Resource.Loading
                        }

                        is Resource.Success -> {
                            _nowPlayingMovies.value = Resource.Success(it.data)
                        }

                        is Resource.Error -> {
                            _nowPlayingMovies.value = Resource.Error(it.exception)
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
                        is Resource.Loading -> {
                            _popularMovies.value = Resource.Loading
                        }

                        is Resource.Success -> {
                            _popularMovies.value = Resource.Success(it.data)
                        }

                        is Resource.Error -> {
                            _popularMovies.value = Resource.Error(it.exception)
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
                        is Resource.Loading -> {
                            _topRatedMovies.value = Resource.Loading
                        }

                        is Resource.Success -> {
                            _topRatedMovies.value = Resource.Success(it.data)
                        }

                        is Resource.Error -> {
                            _topRatedMovies.value = Resource.Error(it.exception)
                        }
                    }
                }
        }
    }
}

