package com.synrgy.mobielib.presentation.ui.main.detailMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.mobielib.common.utils.Resource
import com.synrgy.mobielib.data.remote.response.MovieDetailResponse
import com.synrgy.mobielib.data.repository.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val repo: MovieRepositoryImpl,
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<Resource<MovieDetailResponse>>(Resource.Loading)
    val movieDetail: StateFlow<Resource<MovieDetailResponse>> = _movieDetail
    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            repo.getMovieDetail(movieId).collect { response ->
                _movieDetail.value = response
            }
        }
    }

}