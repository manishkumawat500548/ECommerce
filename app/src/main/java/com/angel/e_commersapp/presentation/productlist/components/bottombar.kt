package com.angel.e_commersapp.presentation.productlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angel.e_commersapp.R


sealed class BottomItem(val route: String, val title: String, val icon: Int) {
    data object Home : BottomItem("home", "Home", R.drawable.homebutton)
    data object WishList : BottomItem("wishList", "WishList", R.drawable.regular_outline_heart)
    data object Cart : BottomItem("cart", "Cart", R.drawable.cart)
    data object Search : BottomItem("search", "Search", R.drawable.search)
    data object Setting : BottomItem("setting", "Setting", R.drawable.outline_account_circle_24)
}

@Composable
fun BottomBar(currentRoute: String, onClick: (BottomItem) -> Unit) {

    val leftItem = listOf(BottomItem.Home, BottomItem.WishList)
    val rightItem = listOf(BottomItem.Search, BottomItem.Setting)
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leftItem.forEach { item ->
                BottomNavItemView(
                    item = item,
                    isSelected = currentRoute == item.route,
                    onClick = { onClick(item) }
                )
            }
            Box(modifier = Modifier.size(60.dp))

            rightItem.forEach { item ->
                BottomNavItemView(
                    item = item,
                    isSelected = currentRoute == item.route,
                    onClick = { onClick(item) }
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp)
                .size(56.dp)
                .shadow(8.dp, CircleShape)
                .background(Color.White, CircleShape)
                .clickable(onClick = {
                    onClick(BottomItem.Cart)
                })
                .padding(8.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.cart),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun BottomNavItemView(item: BottomItem, isSelected: Boolean, onClick: () -> Unit) {
    val iconTint = if (isSelected)  Color(0xff4392f9) else Color.Gray
    val textColor = if (isSelected)  Color(0xff4392f9) else Color.Gray

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp)
    ) {
        Image(
            painter = painterResource(item.icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(iconTint)
        )
        Text(
            item.title,
            fontSize = 12.sp,
            color = textColor,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}