package com.example.shopapplication.di

import android.content.Context
import androidx.room.Room
import com.example.shopapplication.data.local.AppDatabase
import com.example.shopapplication.data.local.dao.CacheMetaDao
import com.example.shopapplication.data.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a singleton instance of the AppDatabase.
     * @param context The application context.
     * @return The AppDatabase instance.
     */
    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext
        context: Context
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()

    /**
     * Provides the ProductDao for database operations related to products.
     * @param db The AppDatabase instance.
     * @return The ProductDao instance.
     */
    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    /**
     * Provides the CacheMetaDao for database operations related to cache metadata.
     * @param db The AppDatabase instance.
     * @return The CacheMetaDao instance.
     */
    @Provides
    fun provideCacheDao(db: AppDatabase): CacheMetaDao = db.cacheMetaDao()
}