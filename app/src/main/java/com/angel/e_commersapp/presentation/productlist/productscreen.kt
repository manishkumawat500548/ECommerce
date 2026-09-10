package com.angel.e_commersapp.presentation.productlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.angel.e_commersapp.R
import com.angel.e_commersapp.navigation.Routes
import com.angel.e_commersapp.presentation.productlist.components.BannerPager
import com.angel.e_commersapp.presentation.productlist.components.BottomBar
import com.angel.e_commersapp.presentation.productlist.components.BottomItem
import com.angel.e_commersapp.presentation.productlist.components.CategorySection
import com.angel.e_commersapp.presentation.productlist.components.ProductCard
import com.angel.e_commersapp.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavController,
    productListViewModel: ProductListViewModel = hiltViewModel()
) {

    val productState by productListViewModel.productState.collectAsState()
    val photoUrl by productListViewModel.photoUrl.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showCategoryFilter by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.height(25.dp)
                    )
                }
            }, navigationIcon = {

                IconButton(onClick = {}) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp), tint = Color.Black
                    )
                }
            }, actions = {
                IconButton(onClick = {}) {
                    if (photoUrl != null) {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color(0xffe0e0e0), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {

                        Image(
                            painter = painterResource(R.drawable.outline_account_circle_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color(0xffe0e0e0), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
    }, bottomBar = {
        BottomBar(currentRoute = "home", onClick = { item ->
            when (item) {
                is BottomItem.Home -> {}
                is BottomItem.WishList -> {
                    navController.navigate(Routes.WishlistScreen)
                }

                is BottomItem.Cart -> {
                    navController.navigate(Routes.CartScreen)
                }

                is BottomItem.Search -> {
                    navController.navigate(Routes.SearchScreen)
                }

                is BottomItem.Setting -> {
                    navController.navigate(Routes.SettingsScreen)
                }

            }
        })
    }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xfff9f9f9))
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(59.dp)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable(onClick = { navController.navigate(Routes.SearchScreen)}),
                placeholder = {
                    Text(
                        "Search any Product...",
                        color = Color(0xffbbbbbb),
                        fontSize = 14.sp
                    )
                }, leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }, trailingIcon = {
                    IconButton(onClick = { navController.navigate(Routes.SearchScreen)}) {
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }, shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ), enabled = false, readOnly = true
            )
            BannerPager()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (val state = productState) {
                    is Result.Success -> {
                        Text(
                            "${state.data.size} Items",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium, color = Color.Black
                        )
                    }

                    else -> {
                        Text("Loading...", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                }

                Row {
                    TextButton(onClick = {}) {
                        Text("Sort")
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    TextButton(onClick = { showCategoryFilter = !showCategoryFilter }) {
                        Text(
                            "Filter",
                            color = if (showCategoryFilter) MaterialTheme.colorScheme.primary else Color.Unspecified
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (showCategoryFilter) MaterialTheme.colorScheme.primary else Color.Unspecified
                        )
                    }
                }
            }

            if (showCategoryFilter) {
                CategorySection(viewModel = productListViewModel)
            }
            when (val state = productState) {
                is Result.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is Result.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.data) { product ->
                            ProductCard(product = product, onClick = {navController.navigate(Routes.ProductDetailScreen(product.id))
                            })
                        }
                    }
                }

                is Result.Failure -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Error ${state.message}", color = Color.Red)
                            Spacer(Modifier.height(6.dp))
                            TextButton(onClick = { productListViewModel.retryLoading() }) {
                                Text("Retry")

                            }
                        }
                    }
                }

                Result.Idle -> {}
            }
        }
    }
}
