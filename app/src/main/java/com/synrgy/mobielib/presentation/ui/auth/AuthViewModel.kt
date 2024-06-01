package com.synrgy.mobielib.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.synrgy.mobielib.data.local.UserModel
import com.synrgy.mobielib.data.repository.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: MovieRepositoryImpl
) : ViewModel() {

    fun login(username: String, password: String) = repo.login(username, password)
    fun register(user: UserModel) = repo.register(user)

    fun checkSession(): LiveData<UserModel> {
        return repo.getSession().asLiveData()
    }

    fun clearSession() = viewModelScope.launch {
        repo.clearSession()
    }

    fun saveSession(user: UserModel) = viewModelScope.launch {
        repo.saveSession(user)
    }
}