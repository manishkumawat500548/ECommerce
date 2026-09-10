package com.angel.e_commersapp.domain.repository

import com.angel.e_commersapp.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface AuthRepository {

    suspend fun logIn(email: String, password: String): Result<String>
    suspend fun signUp(email: String, password: String): Result<String>
    suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<String>

}