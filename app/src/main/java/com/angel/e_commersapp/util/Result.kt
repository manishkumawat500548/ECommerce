package com.angel.e_commersapp.util

import android.os.Message

sealed class Result<out T> {

    data object Idle : Result<Nothing>()
    data object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val message: String) : Result<Nothing>()

}