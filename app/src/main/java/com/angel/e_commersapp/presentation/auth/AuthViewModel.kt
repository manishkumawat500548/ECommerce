package com.angel.e_commersapp.presentation.auth

import android.R.attr.tag
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angel.e_commersapp.domain.usecases.GoogleSignInUseCase
import com.angel.e_commersapp.domain.usecases.LogInUseCase
import com.angel.e_commersapp.domain.usecases.SetUserPrefUseCase
import com.angel.e_commersapp.domain.usecases.SignUpUseCase
import com.angel.e_commersapp.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val setUserPrefUseCase: SetUserPrefUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<Result<String>>(Result.Idle)
    val authState = _authState.asStateFlow()

    fun login(email: String, password: String) {
        _authState.value = Result.Loading

        viewModelScope.launch {
            try {
                val result = logInUseCase(email, password)
                _authState.value = result
                if (result is Result.Success) {
                    setUserPrefUseCase.setLoggedIn(true)
                    setUserPrefUseCase.setFirstTimeLogIn(false)
                }
            } catch (e: Exception) {
                _authState.value = Result.Failure(e.message ?: "Login Failed")
            }

        }
    }


    fun signup(email: String, password: String) {
        _authState.value = Result.Loading

        viewModelScope.launch {
            try {
                val result = signUpUseCase(email, password)
                _authState.value = result

                if (result is Result.Success) {
                    setUserPrefUseCase.setLoggedIn(true)
                    setUserPrefUseCase.setFirstTimeLogIn(false)
                }
            } catch (e: Exception) {
                _authState.value = Result.Failure(e.message ?: "SignUp Failed")
            }
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount){
        _authState.value = Result.Loading
        viewModelScope.launch {
            try {
                val result = googleSignInUseCase(account)
                _authState.value = result

                if (result is Result.Success){
                    setUserPrefUseCase.setLoggedIn(true)
                    setUserPrefUseCase.setFirstTimeLogIn(false)

                }
            }catch (e: Exception){
                Log.e( "con.store.eccamerceapplication.Presentation.Auth. AuthViemModel",  "Exception in Google sign-in: ${e.message}", e)
                _authState.value =Result. Failure(e.message ?: "Google sign-in failed")

            }
        }
    }

    fun resetAuthState(){
        _authState.value = Result.Idle
    }
}