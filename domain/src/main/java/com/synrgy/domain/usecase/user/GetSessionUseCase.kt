package com.synrgy.domain.usecase.user

import com.synrgy.domain.repository.UserRepository
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getSession()
}