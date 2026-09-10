package com.angel.e_commersapp.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angel.e_commersapp.data.remote.dto.CategoryItem
import com.angel.e_commersapp.data.remote.dto.Product
import com.angel.e_commersapp.domain.usecases.GetCategoryUseCase
import com.angel.e_commersapp.domain.usecases.GetProductByCategoryUseCase
import com.angel.e_commersapp.domain.usecases.GetProductUseCase
import com.angel.e_commersapp.domain.usecases.SearchProductUseCase
import com.angel.e_commersapp.util.Result
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val searchProductUseCase: SearchProductUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _productState = MutableStateFlow<Result<List<Product>>>(Result.Idle)
    val productState = _productState.asStateFlow()

    private val _categoryState = MutableStateFlow<Result<List<CategoryItem>>>(Result.Idle)
    val categoryState = _categoryState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()


    private val _photoUrl = MutableStateFlow<String?>(null)
    val photoUrl = _photoUrl.asStateFlow()

    private var searchJob: Job? = null

    private fun loadProfilePhoto() {
        val currentUser = firebaseAuth.currentUser
        _photoUrl.value = currentUser?.photoUrl?.toString()
    }

    init {
        loadProduct()
        loadProfilePhoto()
    }

    fun loadProduct() {
        _productState.value = Result.Loading
        viewModelScope.launch {
            try {
                val result = getProductUseCase()
                _productState.value = result
            } catch (e: Exception) {
                _productState.value = Result.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun retryLoading() {
        loadProduct()
    }

    fun searchProduct(query: String) {
        searchJob?.cancel()
        _selectedCategory.value = null

        searchJob = viewModelScope.launch {
            delay(300)
            _productState.value = Result.Loading
            try {
                val result = searchProductUseCase(query)
                _productState.value = result
            } catch (e: Exception) {
                _productState.value = Result.Failure(e.message ?: "Unknown error")
            }
        }

    }

    private fun loadCategories() {
        _categoryState.value = Result.Loading
        viewModelScope.launch {
            try {
                val result = getCategoryUseCase()
                _categoryState.value = result
            } catch (e: Exception) {
                _categoryState.value = Result.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun filterByCategory(categorySlug: String) {
        _selectedCategory.value = categorySlug
        _productState.value = Result.Loading


        viewModelScope.launch {
            try {
                val result = getProductByCategoryUseCase(categorySlug)
                _productState.value = result
            } catch (e: Exception) {
                _productState.value = Result.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun clearCategoryFilter() {
        _selectedCategory.value = null
        loadProduct()
    }

}