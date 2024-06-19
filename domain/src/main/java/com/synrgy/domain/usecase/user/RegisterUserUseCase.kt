package com.synrgy.domain.usecase.user

import androidx.lifecycle.liveData
import com.synrgy.common.Resource
import com.synrgy.domain.model.User
import com.synrgy.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(user: User) = liveData {
        try {
            val response = userRepository.registerUser(user)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

}