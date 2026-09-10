package com.angel.e_commersapp.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "wishlist")
data class Product(
    @PrimaryKey
    val id: Int,
    val brand: String = "",
    val category: String = "",
    val description: String,
    val discountPercentage: Double = 0.0,
    val images: List<String> = emptyList(),
    val price: Double,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val thumbnail: String = "",
    val title: String,
//    val reviews: List<Review> = emptyList()

    )