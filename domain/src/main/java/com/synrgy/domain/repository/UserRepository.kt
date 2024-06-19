package com.synrgy.domain.repository

import com.synrgy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: User)
    suspend fun loginUser(username: String, password: String): User
    suspend fun updateUser(user: User)
    suspend fun saveSession(user: User)
    suspend fun clearSession()
    fun getSession(): Flow<User>
}