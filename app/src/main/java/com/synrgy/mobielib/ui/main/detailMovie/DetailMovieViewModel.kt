package com.synrgy.mobielib.ui.main.detailMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.common.Resource
import com.synrgy.domain.model.MovieDetailModel
import com.synrgy.domain.usecase.movie.GetDetailMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<Resource<MovieDetailModel>>(Resource.Loading)
    val movieDetail: StateFlow<Resource<MovieDetailModel>> = _movieDetail

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getDetailMovieUseCase.invoke(movieId).collect {
                _movieDetail.value = it
            }
        }
    }

}