package com.angel.e_commersapp.data.local.database

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import com.angel.e_commersapp.data.local.converter.StringListConverter
import com.angel.e_commersapp.data.local.dao.WishlistDao
import com.angel.e_commersapp.data.remote.dto.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class StylishDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
}