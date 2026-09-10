package com.angel.e_commersapp.domain.usecases

import com.angel.e_commersapp.domain.repository.UserPrefRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPrefUseCase @Inject constructor(private val userPrefRepository: UserPrefRepository) {
    fun isFirstTimeLogIn(): Flow<Boolean> = userPrefRepository.isFirstTimeLogIn
    fun isLoggedIn(): Flow<Boolean> = userPrefRepository.isLoggedIn
}