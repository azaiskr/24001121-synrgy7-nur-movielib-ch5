package com.synrgy.mobielib.ui.main

import androidx.lifecycle.ViewModel
import com.synrgy.mobielib.repository.MovieLibRepo

class ListMovieViewModel (private val repo: MovieLibRepo) : ViewModel() {
    fun getMovieListNowPlaying() = repo.getMovieListNowPlaying()
    fun getMovieListPopular() = repo.getMovieListPopular()
    fun getMovieListTopRated() = repo.getMovieListTopRated()
}