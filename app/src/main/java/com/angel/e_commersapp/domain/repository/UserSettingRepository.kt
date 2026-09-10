package com.angel.e_commersapp.domain.repository

import com.angel.e_commersapp.data.remote.dto.UserProfile
import com.angel.e_commersapp.util.Result
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    suspend fun saveUserProfile(userProfile: UserProfile): com.angel.e_commersapp.util.Result<Unit>
    fun getUserProfile(userId: String): Flow<com.angel.e_commersapp.util.Result<UserProfile>>
    suspend fun updateUserProfile(userId: String, updates: Map<String, Any>): Result<Unit>
}
