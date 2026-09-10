package com.angel.e_commersapp.presentation.loginscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LogInViewModel: ViewModel() {

    val userNameAndEmail  = mutableStateOf("")
    val userPassword  = mutableStateOf("")
    val showError  = mutableStateOf(false)
    val errorMessage  = mutableStateOf("")
    val visible  = mutableStateOf(false)

    fun onNameAndEmailChange(value : String){
        userNameAndEmail.value = value
    }
    fun onPasswordChange(value : String){
        userPassword.value = value
    }
}