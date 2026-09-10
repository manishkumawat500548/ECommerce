package com.angel.e_commersapp.presentation.productlist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.angel.e_commersapp.R
import com.angel.e_commersapp.presentation.productlist.ProductListViewModel

@Composable
fun CategorySection(viewModel: ProductListViewModel) {
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val categoryImageMap = mapOf(

        "beauty" to R.drawable.ellipse4,
        "fragrances" to R.drawable.unsplash3q3tsj01nc1,
        "furniture" to R.drawable.unsplashfgdjllzoklo1,
        "mens-shirts" to R.drawable.unsplash_0vsk2_9dkqo1,
        "mens-shoes" to R.drawable.maskgroup,
        "mens-watches" to R.drawable.image184,
        "womens-bags" to R.drawable.image183,
        "womens-dresses" to R.drawable.unsp1,
        "womens-jewellery" to R.drawable.image183,
        "womens-shoes" to R.drawable.maskgroup1,
        "womens-watches" to R.drawable.image184
    )



    val categories = listOf(
        Category("Beauty", R.drawable.ellipse4, "beauty"),
        Category("Fashion", R.drawable.unsplash3q3tsj01nc1, "fragrances"),
        Category("Kids", R.drawable.unsplashfgdjllzoklo1, "furniture"),
        Category("Mens", R.drawable.unsplashxpjylol5ii81, "mens-shirts"),
        Category("Women's", R.drawable.unsplashoye4gi5zq1, "womens-dresses"),
        Category("Gifts", R.drawable.unsplash_pxm8aejbzvk, "gifts")
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category, isSelected = selectedCategory == category.slug,
                onClick = {
                    if (selectedCategory == category.slug) {
                        viewModel.clearCategoryFilter()
                    } else {
                        viewModel.filterByCategory(category.slug)
                    }
                })
        }

    }


}