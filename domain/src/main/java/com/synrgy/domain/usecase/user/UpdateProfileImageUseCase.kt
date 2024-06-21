package com.synrgy.domain.usecase.user

import android.net.Uri
import android.util.Log
import androidx.lifecycle.liveData
import com.synrgy.common.Resource
import com.synrgy.domain.model.User
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