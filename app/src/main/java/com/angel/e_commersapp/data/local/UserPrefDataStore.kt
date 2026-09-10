package com.angel.e_commersapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPrefDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("User_preference")
        private val IS_LOGIN_FIRST_TIME = booleanPreferencesKey("is_login_first_time")
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val isFirstTimeLogIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGIN_FIRST_TIME] ?: true
    }
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    suspend fun setFirstTimeLogin(isFirstTime: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGIN_FIRST_TIME] = isFirstTime
        }
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }
}