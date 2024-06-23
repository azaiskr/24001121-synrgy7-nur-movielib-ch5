package com.synrgy.domain.usecase.user

import android.util.Log
import com.synrgy.domain.repository.UserRepository
import javax.inject.Inject

class UpdateProfileImageUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(uri: String,userEmail:String )  {
        try {
            userRepository.updateProfileImage(profileUri = uri, email = userEmail)
            Log.d("UpdateProfileImageUseCase", "invoke: success $uri")
        } catch (e: Exception) {
            Log.d("UpdateProfileImageUseCase", "invoke: error ${e.message}")
        }
    }

}