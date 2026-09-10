package com.angel.e_commersapp.domain.usecases

import com.angel.e_commersapp.data.remote.dto.CategoryItem
import com.angel.e_commersapp.domain.repository.ProductRepository
import com.angel.e_commersapp.util.Result
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(): Result<List<CategoryItem>> {
        return productRepository.getCategories()
    }
}