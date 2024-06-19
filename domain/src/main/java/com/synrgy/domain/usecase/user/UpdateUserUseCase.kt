package com.synrgy.domain.usecase.user

import androidx.lifecycle.liveData
import com.synrgy.common.Resource
import com.synrgy.domain.model.User
import com.synrgy.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(user: User) = liveData {
        emit(Resource.Loading)
        try {
            val response = userRepository.updateUser(user)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}