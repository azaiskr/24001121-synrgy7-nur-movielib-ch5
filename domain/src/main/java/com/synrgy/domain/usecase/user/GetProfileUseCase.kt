package com.synrgy.domain.usecase.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.synrgy.domain.model.User
import com.synrgy.domain.repository.UserRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(email: String, password: String): LiveData<User> = liveData {
        try {
            val response = userRepository.getUser(email, password)
            emit(response)
        } catch (e: Exception) {
            //
        }
    }
}