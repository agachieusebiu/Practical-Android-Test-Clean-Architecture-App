package com.example.shopapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity to keep track of cache metadata, such as the last updated time for different data keys.
 * This helps in determining when to refresh cached data.
 */
@Entity(tableName = "cache_meta")
data class CacheMetaEntity(
    @PrimaryKey val key: String,
    val lastUpdatedMillis: Long
)