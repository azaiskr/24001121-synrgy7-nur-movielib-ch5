package com.synrgy.data.repository

import com.synrgy.data.SessionPreferences
import com.synrgy.data.local.DatabaseImpl
import com.synrgy.data.local.UserDao
import com.synrgy.data.local.toUser
import com.synrgy.data.local.toUserDataModel
import com.synrgy.domain.model.User
import com.synrgy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val pref: SessionPreferences,
    database: DatabaseImpl,
) : UserRepository {

    private val mUserDao: UserDao = database.userDao()

    override suspend fun registerUser(user: User) {
        val response = mUserDao.insert(user.toUserDataModel())
        return response
    }

    override suspend fun loginUser(username: String, password: String): User {
        val response = mUserDao.getUser(username, password)
        return response.toUser()
    }

    override suspend fun updateUser(user: User) {
        return mUserDao.updateUser(user.toUserDataModel())
    }

    override suspend fun saveSession(user: User) {
        val dataRequest = user.toUserDataModel()
        pref.saveSession(dataRequest)
    }

    override suspend fun clearSession() {
        pref.clearSession()
    }

    override fun getSession(): Flow<User> {
        val response = pref.getSession()
        return response.map { it.toUser() }
    }


}