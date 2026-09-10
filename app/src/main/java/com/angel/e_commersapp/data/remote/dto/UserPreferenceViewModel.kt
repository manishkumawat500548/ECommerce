package com.angel.e_commersapp.data.remote.dto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angel.e_commersapp.domain.usecases.GetUserPrefUseCase
import com.angel.e_commersapp.domain.usecases.SetUserPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPreferencesViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPrefUseCase,
    private val setUserPreferencesUseCase: SetUserPrefUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserPreferencesState())
    val state: StateFlow<UserPreferencesState> = _state.asStateFlow()

    init {
        observeUserPreferences()
    }

    private fun observeUserPreferences() {
        viewModelScope.launch {
            combine(
                getUserPreferencesUseCase.isFirstTimeLogIn(), // false
                getUserPreferencesUseCase.isLoggedIn() // true
            ) { isFirstTime, isLoggedIn ->
                Log.d("com.store.ecommerceapplication.Presentation.UserPreferences.UserPreferencesViewModel", "DataStore values changed - isFirstTime: $isFirstTime, isLoggedIn: $isLoggedIn")
                UserPreferencesState(
                    isFirstTimeLogin = isFirstTime,
                    isLoggedIn = isLoggedIn,
                    isLoading = false
                )
            }.collect { newState ->
                Log.d("com.store.ecommerceapplication.Presentation.UserPreferences.UserPreferencesViewModel", "Updating state - isFirstTime: ${newState.isFirstTimeLogin}, isLoggedIn: ${newState.isLoggedIn}")
                _state.value = newState
            }
        }
    }

}
