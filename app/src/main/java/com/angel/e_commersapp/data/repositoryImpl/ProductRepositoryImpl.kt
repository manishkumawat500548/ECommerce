package com.angel.e_commersapp.data.repositoryImpl

import android.util.Log
import com.angel.e_commersapp.data.remote.ProductApiService
import com.angel.e_commersapp.data.remote.dto.CategoryItem
import com.angel.e_commersapp.data.remote.dto.Product
import com.angel.e_commersapp.domain.repository.ProductRepository
import com.angel.e_commersapp.util.Result

class ProductRepositoryImpl(private val productApiService: ProductApiService) : ProductRepository {
    override suspend fun getProduct(): Result<List<Product>> {
        return try {
            val response = productApiService.getProduct()
            Result.Success(response.products)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    override suspend fun searchProduct(query: String): Result<List<Product>> {
        return try {
            Log.d("ProductRepository", "Searching products with query:$query")
            val response = productApiService.searchProduct(query)
            Log.d("ProductRepository", "Found${response.products.size} products")
            Result.Success(response.products)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error searching products ${e.message}")
            Result.Failure(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    override suspend fun getCategories(): Result<List<CategoryItem>> {
        return try {
            Log.d("ProductRepository", "Fetching categories from API")
            val categories = productApiService.getCategory()
            Log.d("ProductRepository", "Received${categories.size} categories")
            Result.Success(categories)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching categories ${e.message}")
            Result.Failure(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    override suspend fun getProductByCategory(category: String): Result<List<Product>> {
        return try {
            Log.d("ProductRepository", "Fetching products for categories: $category")
            val response = productApiService.getProductByCategory(category)
            Log.d("ProductRepository", "Found ${response.products.size} products in categories")
            Result.Success(response.products)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching products by category ${e.message}", e)
            Result.Failure(e.localizedMessage ?: "Unknown error occurred")
        }
    }
}