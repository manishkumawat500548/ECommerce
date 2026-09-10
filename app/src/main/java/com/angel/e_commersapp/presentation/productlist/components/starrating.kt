package com.angel.e_commersapp.presentation.productlist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(rating: Double, starSize: Dp = 16.dp, maxStar: Int = 5) {
    Row {
        repeat(maxStar) {

            val starRating = when {
                rating >= it + 1 -> 1.0
                rating > it -> rating - it
                else -> 0.0
            }

            Box() {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xffe0e0e0),
                    modifier = Modifier.size(starSize)
                )

                if (starRating > 0) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xffffa726),
                        modifier = Modifier
                            .size(starSize)
                            .clipToBounds()
                            .drawWithContent {
                                clipRect(right = size.width * starRating.toFloat()) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    )
                }
            }
        }


    }

}