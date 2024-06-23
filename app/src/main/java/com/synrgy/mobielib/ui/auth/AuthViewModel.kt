package com.synrgy.mobielib.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.model.User
import com.synrgy.domain.usecase.user.GetProfileUseCase
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
    private val  getProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _user = MediatorLiveData<User>()
    val user: LiveData<User> = _user

    fun getUser(email: String, password: String) = viewModelScope.launch {
        val source = getProfileUseCase(email, password)
        _user.addSource(source) { result ->
            _user.value = result
            _user.removeSource(source)
            Log.d("AuthViewModel", "getUser: $result")
        }
    }

    fun login(username: String, password: String) = loginUseCase.invoke(username, password)

    fun register(user: User) = registerUserUseCase.invoke(user)

    fun checkSession() = getSessionUseCase.invoke().asLiveData()

    fun saveSession(user: User) = viewModelScope.launch {
        saveSessionUseCase.invoke(user)
    }
}