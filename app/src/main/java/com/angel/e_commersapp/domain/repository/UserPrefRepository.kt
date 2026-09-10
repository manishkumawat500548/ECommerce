package com.angel.e_commersapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPrefRepository {

    val isFirstTimeLogIn: Flow<Boolean>
    val isLoggedIn: Flow<Boolean>
    suspend fun setFirstTimeLogIn(isFirstTime: Boolean)
    suspend fun setLoggedIn(isLoggedIn: Boolean)
}