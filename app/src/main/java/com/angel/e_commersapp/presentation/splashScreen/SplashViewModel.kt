package com.angel.e_commersapp.presentation.splashScreen

import androidx.lifecycle.ViewModel
import com.angel.e_commersapp.domain.usecases.GetUserPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserPrefUseCase: GetUserPrefUseCase
) : ViewModel() {

    val isLoggedIn = getUserPrefUseCase.isLoggedIn()
}