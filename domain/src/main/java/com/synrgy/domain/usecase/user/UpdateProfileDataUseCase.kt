package com.synrgy.domain.usecase.user

import androidx.lifecycle.liveData
import com.synrgy.common.Resource
import com.synrgy.domain.model.User
import com.synrgy.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateProfileDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        name: String, phone: String, dob: String, address: String, email: String
    ) = flow {
        emit(Resource.Loading)
        try {
            val response = userRepository.updateProfileData(name, phone, dob, address, email)
            emit(Resource.Success(response))
        }catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}