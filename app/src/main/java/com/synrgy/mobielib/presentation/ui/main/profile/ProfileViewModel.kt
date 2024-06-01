package com.synrgy.mobielib.presentation.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.mobielib.data.repository.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: MovieRepositoryImpl
) : ViewModel() {

    fun clearSession() = viewModelScope.launch {
        repo.clearSession()
    }

}