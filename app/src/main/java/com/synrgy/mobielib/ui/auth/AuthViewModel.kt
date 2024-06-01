package com.synrgy.mobielib.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.synrgy.mobielib.data.local.UserModel
import com.synrgy.mobielib.repository.MovieLibRepo
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: MovieLibRepo
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