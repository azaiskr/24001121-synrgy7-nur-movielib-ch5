package com.synrgy.domain.usecase.user

import com.synrgy.domain.repository.UserRepository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() {
        userRepository.clearSession()
    }

}