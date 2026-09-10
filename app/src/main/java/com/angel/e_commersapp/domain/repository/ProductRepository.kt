package com.angel.e_commersapp.domain.repository

import com.angel.e_commersapp.data.remote.dto.CategoryItem
import com.angel.e_commersapp.data.remote.dto.Product
import com.angel.e_commersapp.util.Result

interface ProductRepository {

    suspend fun getProduct(): Result<List<Product>>
    suspend fun searchProduct(query: String): Result<List<Product>>
    suspend fun getCategories(): Result<List<CategoryItem>>
    suspend fun getProductByCategory(category: String): Result<List<Product>>

}