package com.example.shopapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopapplication.data.local.entity.CacheMetaEntity

@Dao
interface CacheMetaDao {

    /**
     * We use the REPLACE strategy here because we only ever expect to have one
     * entry per key in the table. If we insert a new entry with the same key,
     * we want to update the existing entry.
     * @param meta - The CacheMetaEntity to insert or update.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(meta: CacheMetaEntity)

    /**
     * @param key - The key to look up.
     * @return The last updated time in milliseconds for the given key, or null if
     * the key does not exist.
     * */
    @Query("SELECT lastUpdatedMillis FROM cache_meta WHERE `key` = :key LIMIT 1")
    suspend fun lastUpdated(key: String): Long?
}