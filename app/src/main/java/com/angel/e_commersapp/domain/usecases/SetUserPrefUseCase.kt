package com.angel.e_commersapp.domain.usecases

import com.angel.e_commersapp.domain.repository.UserPrefRepository
import javax.inject.Inject

class SetUserPrefUseCase @Inject constructor(private val userPrefRepository: UserPrefRepository) {

    suspend fun setFirstTimeLogIn(isFirstTime: Boolean){
        userPrefRepository.setFirstTimeLogIn(isFirstTime)
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean){
        userPrefRepository.setFirstTimeLogIn(isLoggedIn)
    }
}