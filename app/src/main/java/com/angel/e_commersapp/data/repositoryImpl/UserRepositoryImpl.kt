package com.angel.e_commersapp.data.repositoryImpl

import com.angel.e_commersapp.data.local.UserPrefDataStore
import com.angel.e_commersapp.domain.repository.UserPrefRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userPrefDataStore: UserPrefDataStore) : UserPrefRepository {

    override val isFirstTimeLogIn: Flow<Boolean> = userPrefDataStore.isFirstTimeLogIn
    override val isLoggedIn: Flow<Boolean> = userPrefDataStore.isLoggedIn


    override suspend fun setFirstTimeLogIn(isFirstTime: Boolean) {
        userPrefDataStore.setFirstTimeLogin(isFirstTime)
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        userPrefDataStore.setLoggedIn(isLoggedIn)
    }

}