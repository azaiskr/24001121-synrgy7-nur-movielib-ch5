package com.synrgy.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.synrgy.data.helper.createTestingDataStore
import com.synrgy.data.local.user.UserDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito.mock

class SessionPreferencesTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var sessionPreferences: SessionPreferences

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        val testDispatcher = UnconfinedTestDispatcher()
        dataStore = createTestingDataStore(TestScope(testDispatcher))
        sessionPreferences = SessionPreferences(dataStore)
    }

    @Test
    fun `saveSession saves user session`() = runTest {
        val user = UserDataModel("username", "email", "password")
        sessionPreferences.saveSession(user)

        val savedUser = sessionPreferences.getSession().first()
        assertEquals(user.email, savedUser.email)
        assertEquals(user.username, savedUser.username)
        assertEquals(user.password, savedUser.password)
    }

    @Test
    fun `clearSession clears user session`() = runTest {
        val user = UserDataModel("username", "email", "password")
        sessionPreferences.saveSession(user)
        sessionPreferences.clearSession()

        val clearedUser = sessionPreferences.getSession().first()
        assertEquals("", clearedUser.email)
        assertEquals("", clearedUser.username)
        assertEquals("", clearedUser.password)
    }

    @Test
    fun `getSession retrieves user session`() = runTest {
        val user = UserDataModel("username", "email", "password")
        sessionPreferences.saveSession(user)

        val session = sessionPreferences.getSession().first()
        assertEquals(user.email, session.email)
        assertEquals(user.username, session.username)
        assertEquals(user.password, session.password)
    }
}
