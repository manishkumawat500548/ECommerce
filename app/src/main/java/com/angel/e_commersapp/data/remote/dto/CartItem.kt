package com.angel.e_commersapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartItem (
    val product : Product,
    val quantity : Int = 1

    )