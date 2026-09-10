package com.angel.e_commersapp.domain.usecases

import com.angel.e_commersapp.domain.repository.AuthRepository
import com.angel.e_commersapp.util.Result
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String,password: String): Result<String>{
        return repository.logIn(email,password)
    }
}