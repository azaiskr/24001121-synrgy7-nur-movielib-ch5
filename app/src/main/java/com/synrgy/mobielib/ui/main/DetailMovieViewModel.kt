package com.synrgy.mobielib.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.mobielib.data.remote.response.MovieDetailResponse
import com.synrgy.mobielib.data.remote.response.MovieListResponse
import com.synrgy.mobielib.repository.MovieLibRepo
import com.synrgy.mobielib.utils.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailMovieViewModel (private val repo: MovieLibRepo) : ViewModel() {

    private val _movieDetail = MutableStateFlow<Response<MovieDetailResponse>>(Response.Loading)
    val movieDetail: StateFlow<Response<MovieDetailResponse>> = _movieDetail
    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            repo.getMovieDetail(movieId).collect { response ->
                _movieDetail.value = response
            }
        }
    }

}