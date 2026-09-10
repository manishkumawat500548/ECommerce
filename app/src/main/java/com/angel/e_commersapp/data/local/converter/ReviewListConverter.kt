package com.angel.e_commersapp.data.local.converter

import androidx.room.TypeConverter
import com.angel.e_commersapp.data.remote.dto.Review
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ReviewListConverter {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @TypeConverter
    fun fromReviewList(value: List<Review>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toReviewList(value: String): List<Review> {
        return try {
            json.decodeFromString<List<Review>>(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
