package com.angel.e_commersapp.data.repositoryImpl

import android.util.Log
import com.angel.e_commersapp.data.remote.dto.UserProfile
import com.angel.e_commersapp.domain.repository.UserSettingsRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import com.angel.e_commersapp.util.Result

@Singleton
class UserSettingsRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : UserSettingsRepository {

    private val usersRef: DatabaseReference = database.getReference("users")

    override suspend fun saveUserProfile(userProfile: UserProfile): Result<Unit> {
        return try {
            Log.d("UserSettingsRepo", "Attempting to save profile for userId: ${userProfile.userId}")

            if (userProfile.userId.isEmpty()) {
                Log.e("UserSettingsRepo", "User ID is empty")
                return Result.Failure("User ID is empty")
            }

            // Convert to HashMap for better Firebase compatibility
            val profileMap = hashMapOf<String, Any>(
                "userId" to userProfile.userId,
                "emailAddress" to userProfile.emailAddress,
                "password" to userProfile.password,
                "pincode" to userProfile.pincode,
                "address" to userProfile.address,
                "city" to userProfile.city,
                "state" to userProfile.state,
                "country" to userProfile.country,
                "bankAccountNumber" to userProfile.bankAccountNumber,
                "accountHolderName" to userProfile.accountHolderName,
                "ifscCode" to userProfile.ifscCode
            )

            usersRef.child(userProfile.userId).setValue(profileMap).await()
            Log.d("UserSettingsRepo", "Profile saved successfully")
            com.angel.e_commersapp.util.Result.Success(Unit)
        } catch (e: Exception) {
            Log.e("UserSettingsRepo", "Error saving profile: ${e.message}", e)
            Result.Failure(e.message ?: "Failed to save user profile: ${e.message}")
        }
    }

    override fun getUserProfile(userId: String): Flow<Result<UserProfile>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val userProfile = snapshot.getValue(UserProfile::class.java)
                    if (userProfile != null) {
                        trySend(com.angel.e_commersapp.util.Result.Success(userProfile))
                    } else {
                        // Return empty profile if no data exists
                        trySend(com.angel.e_commersapp.util.Result.Success(UserProfile(userId = userId)))
                    }
                } catch (e: Exception) {
                    trySend(Result.Failure(e.message ?: "Failed to parse user profile"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.Failure(error.message))
            }
        }

        usersRef.child(userId).addValueEventListener(listener)

        awaitClose {
            usersRef.child(userId).removeEventListener(listener)
        }
    }

    override suspend fun updateUserProfile(userId: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            usersRef.child(userId).updateChildren(updates).await()
            com.angel.e_commersapp.util.Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Failed to update user profile")
        }
    }
}
