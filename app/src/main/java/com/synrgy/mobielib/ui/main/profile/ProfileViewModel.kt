package com.synrgy.mobielib.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.domain.model.User
import com.synrgy.domain.usecase.user.ClearSessionUseCase
import com.synrgy.domain.usecase.user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearSessionUseCase: ClearSessionUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) : ViewModel() {

    fun clearSession() = viewModelScope.launch {
        clearSessionUseCase.invoke()
    }

    fun updateUserData(userData: User) = viewModelScope.launch {
        updateUserUseCase.invoke(userData)
    }

}