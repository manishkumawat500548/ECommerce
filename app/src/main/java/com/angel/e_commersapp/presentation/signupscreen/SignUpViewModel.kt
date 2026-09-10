package com.angel.e_commersapp.presentation.signupscreen

import androidx.compose.runtime.annotation.FrequentlyChangingValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {

    val userNameAndEmail = mutableStateOf("")
    val userPassword = mutableStateOf("")
    val userConfPassword = mutableStateOf("")
    val errorMessage = mutableStateOf<String?>(null)
    val visible  = mutableStateOf(false)


    fun onUserNameAndPasswordChange(value: String){
        userNameAndEmail.value = value
    }
    fun onUserPasswordChange(value: String){
        userPassword.value = value
    }
    fun onUserCongPasswordChange(value: String){
        userConfPassword.value = value
    }

}