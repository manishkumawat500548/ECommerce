package com.angel.e_commersapp.presentation.productlist.components

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.angel.e_commersapp.data.remote.dto.Product

@SuppressLint("DefaultLocale")
@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {

    val context = LocalContext.current

    @SuppressLint("DefaultLocale")
    fun shareProduct(product: Product) {
        val shareText = "Check out this amazing product : ${product.title}\n\n" +
                "${product.description}\n\n" + "price : ₹${
            String.format(
                "%.0f",
                product.price * 83
            )
        }\n" +
                "Rating : ${
                    String.format(
                        "%.1f",
                        product.rating
                    )
                } stars\n\n" + "Get it now on EcoMart"

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "Check out ${product.title}")
        }
        val chooserIntent = Intent.createChooser(shareIntent, "Share Product")
        context.startActivity(chooserIntent)

    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context).data(product.thumbnail).crossfade(true)
                    .build(),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xfff5f5f5)),
                contentScale = ContentScale.Fit,
                loading = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }, error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xfff5f5f5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Image", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            )
            Spacer(Modifier.height(8.dp))

            Text(
                product.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
            Spacer(Modifier.height(4.dp))
            Text(
                product.description,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(
                        "₹${String.format("%.0f", product.price * 83)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    if (product.discountPercentage > 0) {
                        val originalPrice = product.price / (1 - product.discountPercentage / 100)
                        Text(
                            "₹${String.format("%.0f", originalPrice * 83)}",
                            fontSize = 12.sp,
                            color = Color.Gray, textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    StarRating(rating = product.rating, starSize = 14.dp)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        String.format("%.1f", product.rating), fontSize = 12.sp, color = Color.Gray
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = { shareProduct(product) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )

                    }
                }
            }

        }
    }
}