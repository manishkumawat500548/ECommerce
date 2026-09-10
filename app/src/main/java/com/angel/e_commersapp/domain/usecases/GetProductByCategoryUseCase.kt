package com.angel.e_commersapp.domain.usecases

import com.angel.e_commersapp.data.remote.dto.Product
import com.angel.e_commersapp.domain.repository.ProductRepository
import com.angel.e_commersapp.util.Result
import javax.inject.Inject

class GetProductByCategoryUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(category: String): Result<List<Product>> {
        return productRepository.getProductByCategory(category)
    }
}