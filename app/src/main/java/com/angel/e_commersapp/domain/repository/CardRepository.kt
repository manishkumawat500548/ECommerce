package com.angel.e_commersapp.domain.repository

import com.angel.e_commersapp.data.remote.dto.Product
import com.angel.e_commersapp.data.remote.dto.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(product: Product, quantity: Int = 1)
    suspend fun removeFromCart(productId: Int)
    suspend fun updateQuantity(productId: Int, quantity: Int)
    suspend fun clearCart()
    suspend fun getCartItemCount(): Int
}
