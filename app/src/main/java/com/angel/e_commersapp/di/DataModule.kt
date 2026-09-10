package com.angel.e_commersapp.di

import android.content.Context
import androidx.room.Room
import com.angel.e_commersapp.data.local.CartDataStore
import com.angel.e_commersapp.data.local.dao.WishlistDao
import com.angel.e_commersapp.data.local.database.StylishDatabase
import com.angel.e_commersapp.data.remote.ProductApiService
import com.angel.e_commersapp.data.local.UserPrefDataStore
import com.angel.e_commersapp.data.repositoryImpl.AuthRepositoryImpl
import com.angel.e_commersapp.data.repositoryImpl.CartRepositoryImpl
import com.angel.e_commersapp.data.repositoryImpl.ProductRepositoryImpl
import com.angel.e_commersapp.data.repositoryImpl.UserRepositoryImpl
import com.angel.e_commersapp.data.repositoryImpl.UserSettingsRepositoryImpl
import com.angel.e_commersapp.data.repositoryImpl.WishlistRepositoryImpl
import com.angel.e_commersapp.domain.repository.AuthRepository
import com.angel.e_commersapp.domain.repository.CartRepository
import com.angel.e_commersapp.domain.repository.ProductRepository
import com.angel.e_commersapp.domain.repository.UserPrefRepository
import com.angel.e_commersapp.domain.repository.UserSettingsRepository
import com.angel.e_commersapp.domain.repository.WishListRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        // Use the default database URL based on project ID
        val databaseUrl = "https://ecommerce-application-67207-default-rtdb.firebaseio.com/"
        return FirebaseDatabase.getInstance(databaseUrl)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(@ApplicationContext context: Context): UserPrefDataStore {
        return UserPrefDataStore(context)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                url("https://dummyjson.com/")
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun provideProductApiService(httpClient: HttpClient): ProductApiService {
        return ProductApiService(httpClient)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productApiService: ProductApiService): ProductRepository {
        return ProductRepositoryImpl(productApiService)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(userPreferencesDataStore: UserPrefDataStore): UserPrefRepository {
        return UserRepositoryImpl(userPreferencesDataStore)
    }

    @Provides
    @Singleton
    fun provideEcoMartDatabase(@ApplicationContext context: Context): StylishDatabase {
        return Room.databaseBuilder(
            context,
            StylishDatabase::class.java,
            "ecomart_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWishlistDao(database: StylishDatabase): WishlistDao {
        return database.wishlistDao()
    }

    @Provides
    @Singleton
    fun provideWishlistRepository(wishlistDao: WishlistDao): WishListRepository {
        return WishlistRepositoryImpl(wishlistDao)
    }

    @Provides
    @Singleton
    fun provideCartDataStore(@ApplicationContext context: Context): CartDataStore {
        return CartDataStore(context)
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDataStore: CartDataStore): CartRepository {
        return CartRepositoryImpl(cartDataStore)
    }

    @Provides
    @Singleton
    fun provideUserSettingsRepository(database: FirebaseDatabase): UserSettingsRepository {
        return UserSettingsRepositoryImpl(database)
    }
}
