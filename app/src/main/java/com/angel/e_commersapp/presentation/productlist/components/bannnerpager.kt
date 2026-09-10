package com.angel.e_commersapp.presentation.productlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.angel.e_commersapp.R
import kotlinx.coroutines.delay

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BannerPager() {

    val banners = listOf(
        R.drawable.banner_1,
        R.drawable.banner_2,
        R.drawable.banner_3
    )

    val pageState = rememberPagerState(pageCount = { banners.size })

    LaunchedEffect(Unit) {

        while (true) {
            delay(3000)
            val nextPage = (pageState.currentPage + 1) % banners.size
            pageState.animateScrollToPage(nextPage)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {

        HorizontalPager(
            pageState, modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) { page ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Image(
                    painter = painterResource(banners[page]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(banners.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pageState.currentPage == index) 8.dp else 6.dp)
                        .clip(
                            CircleShape
                        )
                        .background(
                            if (pageState.currentPage == index) Color.White else Color.White.copy(
                                .5f
                            )
                        )
                )
            }
        }
    }
}