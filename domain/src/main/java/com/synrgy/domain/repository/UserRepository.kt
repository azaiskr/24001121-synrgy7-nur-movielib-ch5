package com.synrgy.domain.repository
import com.synrgy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: User)
    suspend fun getUser(username: String, password: String): User
    suspend fun updateProfileImage (profileUri: String, email: String)
    suspend fun updateProfileData(name: String, phone: String, dob: String, address: String, email: String)
    suspend fun saveSession(user: User)
    suspend fun clearSession()
    fun getSession(): Flow<User>
}