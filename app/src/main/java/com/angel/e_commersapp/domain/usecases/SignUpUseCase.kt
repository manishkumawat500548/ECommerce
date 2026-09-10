package com.angel.e_commersapp.domain.usecases

import com.angel.e_commersapp.domain.repository.AuthRepository
import com.angel.e_commersapp.util.Result
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<String> {

        if (email.isBlank()) {
            return Result.Failure(message = "Email can't be empty")
        }
        if (email.length < 6) {
            return Result.Failure(message = "Password must be at least 6 letter")
        }
        if (!email.contains("@") && !email.contains(".")) {
            return Result.Failure(message = "Invalid email format")
        }
        return repository.signUp(email, password)
    }
}