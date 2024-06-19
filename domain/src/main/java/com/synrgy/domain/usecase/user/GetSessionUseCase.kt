package com.synrgy.domain.usecase.user

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.synrgy.common.Resource
import com.synrgy.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getSession()
}