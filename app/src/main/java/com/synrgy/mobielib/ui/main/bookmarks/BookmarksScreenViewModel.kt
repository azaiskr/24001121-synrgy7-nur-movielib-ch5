package com.synrgy.mobielib.ui.main.bookmarks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.model.MovieListModel
import com.synrgy.domain.usecase.movie.GetFavMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksScreenViewModel @Inject constructor(
    getFavMoviesUseCase: GetFavMoviesUseCase
): ViewModel(){

    val favMovies = getFavMoviesUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}