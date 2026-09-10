package com.angel.e_commersapp.data.remote.dto


data class UserPreferencesState(
    val isFirstTimeLogin: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true
)