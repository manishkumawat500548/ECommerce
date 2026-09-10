package com.angel.e_commersapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.angel.e_commersapp.data.remote.dto.UserPreferencesViewModel
import com.angel.e_commersapp.presentation.auth.ForgotPasswordScreen
import com.angel.e_commersapp.presentation.cart.CartScreen
import com.angel.e_commersapp.presentation.loginscreen.LogInScreen
import com.angel.e_commersapp.presentation.onBoardingScreen.OnBoardingScreen
import com.angel.e_commersapp.presentation.productlist.ProductDetailScreen
import com.angel.e_commersapp.presentation.productlist.ProductScreen
import com.angel.e_commersapp.presentation.search.SearchScreen
import com.angel.e_commersapp.presentation.setting.SettingsScreen
import com.angel.e_commersapp.presentation.signupscreen.SignUpScreen
import com.angel.e_commersapp.presentation.splashScreen.SplashScreen
import com.angel.e_commersapp.presentation.wishlist.WishlistScreen

@Composable
fun AppNavigation() {
    var navController = rememberNavController()

    // Inject ViewModels using Hilt
    val userPreferencesViewModel: UserPreferencesViewModel = hiltViewModel()

    // Observe user preferences state
    val userPreferencesState by userPreferencesViewModel.state.collectAsState()

    NavHost(navController = navController, startDestination = Routes.SplashScreen) {

        composable<Routes.LoginScreen> {
            LogInScreen(
                navController = navController,
            )
        }

        composable<Routes.SignUpScreen> {
            SignUpScreen(
                navController = navController
            )
        }

        composable<Routes.ForgetPasswordScreen> {
            ForgotPasswordScreen(navController)
        }


        composable<Routes.OnboardingScreen> {
            OnBoardingScreen(
                navController = navController
            )
        }

        composable<Routes.ProductListScreen> {
            ProductScreen(
                navController = navController
            )
        }

        composable<Routes.ProductDetailScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Routes.ProductDetailScreen>()
            ProductDetailScreen(
                navController = navController,
                productId = args.productId
            )
        }

        composable<Routes.SplashScreen> {
                SplashScreen(
                    isLoading = userPreferencesState.isLoading,
                    onFinish = {
                        val destination = when {
                            userPreferencesState.isLoggedIn -> Routes.ProductListScreen
                            userPreferencesState.isFirstTimeLogin -> Routes.OnboardingScreen
                            else -> Routes.LoginScreen
                        }

                        navController.navigate(destination) {
                            popUpTo(Routes.SplashScreen) { inclusive = true }
                        }
                    }
                )
            }

        composable<Routes.WishlistScreen> {
            WishlistScreen(
                navController = navController
            )
        }

        composable<Routes.CartScreen> {
            CartScreen(
                navController = navController
            )
        }

        composable<Routes.SearchScreen> {
            SearchScreen(
                navController = navController
            )
        }

        composable<Routes.SettingsScreen> {
            SettingsScreen(
                navController = navController
            )
        }
    }
}
