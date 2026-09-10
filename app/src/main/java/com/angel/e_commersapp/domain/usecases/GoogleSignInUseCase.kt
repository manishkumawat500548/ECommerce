package com.angel.e_commersapp.domain.usecases

import com.angel.e_commersapp.domain.repository.AuthRepository
import com.angel.e_commersapp.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(account: GoogleSignInAccount): Result<String> {
        return repository.signInWithGoogle(account)
    }

}