package com.angel.e_commersapp.data.repositoryImpl

import com.angel.e_commersapp.data.local.CartDataStore
import com.angel.e_commersapp.data.remote.dto.Product
import com.angel.e_commersapp.data.remote.dto.CartItem
import com.angel.e_commersapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val cartDataStore: CartDataStore
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDataStore.cartItems
    }

    override suspend fun addToCart(product: Product, quantity: Int) {
        cartDataStore.addToCart(product, quantity)
    }

    override suspend fun removeFromCart(productId: Int) {
        cartDataStore.removeFromCart(productId)
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        cartDataStore.updateQuantity(productId, quantity)
    }

    override suspend fun clearCart() {
        cartDataStore.clearCart()
    }

    override suspend fun getCartItemCount(): Int {
        return cartDataStore.getCartItemCount()
    }
}
