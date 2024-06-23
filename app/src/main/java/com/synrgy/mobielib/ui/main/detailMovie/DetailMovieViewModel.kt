package com.synrgy.mobielib.ui.main.detailMovie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.common.Resource
import com.synrgy.domain.model.MovieDetailModel
import com.synrgy.domain.model.MovieListModel
import com.synrgy.domain.usecase.movie.AddFavMovieUseCase
import com.synrgy.domain.usecase.movie.DeleteFavMovieUseCase
import com.synrgy.domain.usecase.movie.GetDetailMovieUseCase
import com.synrgy.domain.usecase.movie.GetFavMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getFavMoviesUseCase: GetFavMoviesUseCase,
    private val addFavMovieUseCase: AddFavMovieUseCase,
    private val deleteFavMovieUseCase: DeleteFavMovieUseCase
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<Resource<MovieDetailModel>>(Resource.Loading)
    val movieDetail: StateFlow<Resource<MovieDetailModel>> = _movieDetail

    val favMovies = getFavMoviesUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addFavMovie(movie: MovieListModel) = viewModelScope.launch {
        try{
            addFavMovieUseCase.invoke(movie)
            Log.d("DetailMovieViewModel", "addFavMovie: success")
        }catch (e: Exception){
            e.printStackTrace()
            Log.d("DetailMovieViewModel", "addFavMovie: ${e.message}")
        }
    }

    fun deleteFavMovie(movie: MovieListModel) = viewModelScope.launch {
        try{
            deleteFavMovieUseCase.invoke(movie)
            Log.d("DetailMovieViewModel", "deleteFavMovie: success")
        }catch (e: Exception){
            e.printStackTrace()
            Log.d("DetailMovieViewModel", "deleteFavMovie: ${e.message}")
        }
    }

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getDetailMovieUseCase.invoke(movieId).collect {
                _movieDetail.value = it
            }
        }
    }

}