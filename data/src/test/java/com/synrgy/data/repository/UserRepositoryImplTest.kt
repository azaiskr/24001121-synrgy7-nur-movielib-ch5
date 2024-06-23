package com.synrgy.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.synrgy.data.SessionPreferences
import com.synrgy.data.helper.createTestingDataStore
import com.synrgy.data.local.FakeDatabase
import com.synrgy.data.local.movie.FakeMovieDao
import com.synrgy.data.local.user.FakeUserDao
import com.synrgy.domain.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    private lateinit var userDao: FakeUserDao
    private lateinit var movieDao: FakeMovieDao
    private lateinit var database: FakeDatabase
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var sessionPreferences: SessionPreferences
    private lateinit var dataStore: DataStore<Preferences>

    @Before
    fun setUp() {
        userDao = FakeUserDao()
        movieDao = FakeMovieDao()
        database = FakeDatabase(userDao, movieDao)
        dataStore = createTestingDataStore(TestScope())
        sessionPreferences = SessionPreferences(dataStore)
        userRepository = UserRepositoryImpl(sessionPreferences, database)
    }

    @Test
    fun `registerUser inserts user into database`() = runTest {
        val user = User("username", "email", "password")
        userRepository.registerUser(user)

        val retrievedUser = userDao.getUser("email", "password")
        assertEquals(user.email, retrievedUser.email)
    }

    @Test
    fun `getUser retrieves user from database`() = runTest {
        val user = User("username", "email", "password")
        userRepository.registerUser(user)

        val retrievedUser = userRepository.getUser("email", "password")
        assertEquals(user.email, retrievedUser.email)
    }

    @Test
    fun `updateProfileImage updates the user's profile image`() = runTest {
        val user = User("username", "email", "password")
        userRepository.registerUser(user)
        userRepository.updateProfileImage("newProfileImage.jpg", "email")

        val updatedUser = userDao.getUser("email", "password")
        assertEquals("newProfileImage.jpg", updatedUser.profileImg)
    }

    @Test
    fun `updateProfileData updates the user's profile data`() = runTest {
        val user = User("username", "email", "password")
        userRepository.registerUser(user)
        userRepository.updateProfileData("newName", "newPhone", "newDob", "newAddress", "email")

        val updatedUser = userDao.getUser("email", "password")
        assertEquals("newName", updatedUser.name)
        assertEquals("newPhone", updatedUser.phone)
        assertEquals("newDob", updatedUser.dob)
        assertEquals("newAddress", updatedUser.address)
    }
}