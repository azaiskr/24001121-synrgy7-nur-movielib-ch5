package com.synrgy.mobielib.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.model.User
import com.synrgy.domain.usecase.user.ClearSessionUseCase
import com.synrgy.domain.usecase.user.GetSessionUseCase
import com.synrgy.domain.usecase.user.LoginUserUseCase
import com.synrgy.domain.usecase.user.RegisterUserUseCase
import com.synrgy.domain.usecase.user.SaveSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
    private val clearSessionUseCase: ClearSessionUseCase,
) : ViewModel() {


    fun login(username: String, password: String) = loginUseCase.invoke(username, password)

    fun register(user: User) = registerUserUseCase.invoke(user)

    fun checkSession() = getSessionUseCase.invoke().asLiveData()

    fun clearSession() = viewModelScope.launch {
        clearSessionUseCase.invoke()
    }

    fun saveSession(user: User) = viewModelScope.launch {
        saveSessionUseCase.invoke(user)
    }
}