package com.angel.e_commersapp.domain.usecases

import com.angel.e_commersapp.data.remote.dto.Product
import com.angel.e_commersapp.domain.repository.ProductRepository
import com.angel.e_commersapp.util.Result
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return if (query.isNotBlank()) {
            productRepository.getProduct()
        } else {
            productRepository.searchProduct(query)
        }
    }
}