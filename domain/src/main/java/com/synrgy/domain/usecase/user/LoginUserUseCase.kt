package com.synrgy.domain.usecase.user

import androidx.lifecycle.liveData
import com.synrgy.common.Resource
import com.synrgy.domain.model.User
import com.synrgy.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(username: String, password: String) = liveData {
        emit(Resource.Loading)
        try {
            val response = userRepository.getUser(username, password)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }

    }
}