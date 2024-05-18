package com.synrgy.mobielib.ui.main

import androidx.lifecycle.ViewModel
import com.synrgy.mobielib.repository.MovieLibRepo

class DetailMovieViewModel (private val repo: MovieLibRepo) : ViewModel() {

    fun getMovieDetail(movieId: Int) = repo.getMovieDetail(movieId)

}