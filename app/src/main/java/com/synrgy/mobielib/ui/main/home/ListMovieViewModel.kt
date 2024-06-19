package com.synrgy.mobielib.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.common.Resource
import com.synrgy.domain.model.MovieListModel
import com.synrgy.domain.usecase.movie.GetListNowPlayingUseCase
import com.synrgy.domain.usecase.movie.GetListTopRatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(
    private val nowPlayingUseCase: GetListNowPlayingUseCase,
    private val topRatedUseCase: GetListTopRatedUseCase,
    private val popularUseCase: GetListTopRatedUseCase,
) : ViewModel() {
    private val _nowPlayingMovies =
        MutableStateFlow<Resource<List<MovieListModel>>>(Resource.Loading)
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow<Resource<List<MovieListModel>>>(Resource.Loading)
    val popularMovies = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<Resource<List<MovieListModel>>>(Resource.Loading)
    val topRatedMovies = _topRatedMovies.asStateFlow()

    fun getMovieListNowPlaying() {
        viewModelScope.launch {
            nowPlayingUseCase.invoke()
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
            popularUseCase.invoke()
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
            topRatedUseCase.invoke()
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

