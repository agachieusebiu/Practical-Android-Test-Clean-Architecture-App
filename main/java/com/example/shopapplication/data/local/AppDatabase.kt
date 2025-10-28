package com.example.shopapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shopapplication.data.local.dao.CacheMetaDao
import com.example.shopapplication.data.local.dao.ProductDao
import com.example.shopapplication.data.local.entity.CacheMetaEntity
import com.example.shopapplication.data.local.entity.GradeEntity
import com.example.shopapplication.data.local.entity.ProductL1Entity
import com.example.shopapplication.data.local.entity.ProductL2Entity

@Database(
    /**
     * The list of entities (tables) included in the database.
     * */
    entities = [
        ProductL1Entity::class,
        ProductL2Entity::class,
        GradeEntity::class,
        CacheMetaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Provides access to ProductDao for performing database operations related to products.
     * */
    abstract fun productDao(): ProductDao

    /**
     * Provides access to CacheMetaDao for performing database operations related to cache metadata.
     * */
    abstract fun cacheMetaDao(): CacheMetaDao
}