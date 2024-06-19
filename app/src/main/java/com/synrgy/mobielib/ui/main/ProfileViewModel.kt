package com.synrgy.mobielib.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.mobielib.repository.MovieLibRepo
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repo: MovieLibRepo
) : ViewModel() {

    fun clearSession() = viewModelScope.launch {
        repo.clearSession()
    }

}