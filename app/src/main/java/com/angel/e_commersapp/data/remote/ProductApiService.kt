package com.angel.e_commersapp.data.remote

import com.angel.e_commersapp.data.remote.dto.CategoryItem
import com.angel.e_commersapp.data.remote.dto.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProductApiService(private val client: HttpClient) {

    suspend fun getProduct(limit: Int = 0): ProductResponse {
        return client.get("products") {
            parameter("limit", limit)
        }.body()
    }

    suspend fun searchProduct(query: String): ProductResponse {
        return client.get("products/search") {
            parameter("q", query)
        }.body()
    }

    suspend fun getCategory(): List<CategoryItem> {
        return client.get("products/categories").body()
    }

    suspend fun getProductByCategory(category: String): ProductResponse {
        return client.get("product/category/$category").body()
    }
}