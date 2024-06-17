package com.synrgy.mobielib.domain.repository

import com.synrgy.mobielib.common.utils.Resource
import com.synrgy.mobielib.data.local.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: UserModel): Resource<UserModel>
    suspend fun loginUser(user: UserModel): Resource<UserModel>
    suspend fun saveSession(user: UserModel)
    suspend fun clearSession()
    fun getSession(): Flow<UserModel>
}