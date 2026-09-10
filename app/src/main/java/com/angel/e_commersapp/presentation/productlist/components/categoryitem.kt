package com.angel.e_commersapp.presentation.productlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import io.ktor.http.ContentRange

data class Category(val name: String, val imageRes: Int, val slug: String)

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(64.dp)
            .clickable(onClick = {onClick()})
    ) {
        Image(
            painter = painterResource(category.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(
                    CircleShape
                )
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xffe0e0e0),
                    shape = CircleShape
                ), contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(4.dp))

        Text(
            category.name, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.Black,
            maxLines = 1, overflow = TextOverflow.Ellipsis
        )
    }

}