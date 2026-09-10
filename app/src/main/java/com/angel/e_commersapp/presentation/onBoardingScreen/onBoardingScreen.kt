package com.angel.e_commersapp.presentation.onBoardingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.angel.e_commersapp.R
import com.angel.e_commersapp.fontBold
import com.angel.e_commersapp.fontMedium
import com.angel.e_commersapp.navigation.Routes
import com.angel.e_commersapp.ui.theme.blue
import kotlinx.coroutines.launch

data class OnBoardingPage(val title: String, val des: String, val image: Int)

val onBoardingPages = listOf(
    OnBoardingPage(
        "Choose Products",
        "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet Sint. Velit officia consequat duis enim.",
        R.drawable.a_flat_style_digital_illustration_depicts_the_inte
    ),
    OnBoardingPage(
        "Make Payment",
        "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet Sint. Velit officia consequat duis enim.",
        R.drawable.a_flat_style_digital_illustration_features_two_wom
    ),
    OnBoardingPage(
        "Get Your Order",
        "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet Sint. Velit officia consequat duis enim.",
        R.drawable.a_flat_style_digital_illustration_features_a_young
    )
)

@Composable
fun OnBoardingScreen(navController: NavController) {
    val pageState = rememberPagerState(pageCount = { onBoardingPages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = {}) {
                Text("Skip", color = Color.Black, fontFamily = fontMedium, fontSize = 18.sp)
            }
        }
        HorizontalPager(state = pageState, modifier = Modifier.weight(1f)) {
            OnBoardingContent(page = onBoardingPages[it])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { scope.launch { pageState.animateScrollToPage(pageState.currentPage - 1) } },
                enabled = pageState.currentPage > 0
            ) {
                Text(
                    if (pageState.currentPage > 0) "Prev" else "",
                    color = Color.Gray,
                    fontFamily = fontMedium, fontSize = 18.sp
                )
            }

            Row {
                repeat(pageState.pageCount) {
                    val color = if (pageState.currentPage == it) Color.Black else Color.Gray
                    Box(
                        modifier = Modifier.padding(4.dp)
                            .width(if (pageState.currentPage == it) 40.dp else 12.dp)
                            .height(6.dp)
                            .background(
                                color, CircleShape
                            )
                    )
                }
            }
            TextButton(onClick = {
                if (pageState.currentPage < onBoardingPages.size - 1) {
                    scope.launch {
                        pageState.animateScrollToPage(pageState.currentPage + 1)
                    }
                }
                else {
                    navController.navigate(Routes.LoginScreen) {
                        popUpTo(Routes.OnboardingScreen) {
                            inclusive = true
                        }
                    }
                }
            }) {
                Text(
                    if (pageState.currentPage == onBoardingPages.size - 1) "Get Started" else "Next",
                    fontFamily = fontMedium,
                    color = blue, fontSize = 18.sp
                )

            }
        }
    }
}

@Composable
fun OnBoardingContent(page: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            page.title,
            fontFamily = fontBold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            page.des,
            fontFamily = fontMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray, lineHeight = 24.sp
        )
    }
}