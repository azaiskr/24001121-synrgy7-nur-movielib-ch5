package com.synrgy.mobielib.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.synrgy.mobielib.data.local.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
            username = preferences[USERNAME_KEY] ?: "",
            email = preferences[EMAIL_KEY] ?: "",
            password = preferences[PASSWORD_KEY] ?: ""
            )
        }
    }

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
    }
}