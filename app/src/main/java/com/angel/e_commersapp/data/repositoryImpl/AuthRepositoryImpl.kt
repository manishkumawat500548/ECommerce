package com.angel.e_commersapp.data.repositoryImpl

import com.angel.e_commersapp.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import  com.angel.e_commersapp.util.Result
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    override suspend fun logIn(
        email: String,
        password: String
    ): Result<String> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.Success("LogIn Successfully")

        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown error found")

        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
//            firebaseAuth.signOut()
            Result.Success("SignUp Successfully")

        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown error during signUp")

        }
    }

    override suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<String> {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            Result.Success("Google signIn successful")
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown error during signIn")
        }
    }
}