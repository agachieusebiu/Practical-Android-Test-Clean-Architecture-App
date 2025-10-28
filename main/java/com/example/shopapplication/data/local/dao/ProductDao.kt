package com.example.shopapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopapplication.data.local.entity.GradeEntity
import com.example.shopapplication.data.local.entity.ProductL1Entity
import com.example.shopapplication.data.local.entity.ProductL2Entity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    /**
     * @return A Flow that emits the list of ProductL1Entity from the database.
     * */
    @Query("SELECT * FROM products_l1")
    fun observeFirstProductsList(): Flow<List<ProductL1Entity>>

    /**
     * @param items - The list of ProductL1Entity to insert or update.
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFirstProductsList(items: List<ProductL1Entity>)

    /**
     * clears all entries in the products_l1 table.
     * */
    @Query("DELETE FROM products_l1")
    suspend fun clearFirstProductsList()

    /**
     * @return A Flow that emits the list of ProductL2Entity from the database.
     * */
    @Query("SELECT * FROM products_l2")
    fun observeSecondProductsList(): Flow<List<ProductL2Entity>>

    /**
     * @param items - The list of ProductL2Entity to insert or update.
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSecondProductsList(items: List<ProductL2Entity>)

    /**
     * clears all entries in the products_l2 table.
     * */
    @Query("DELETE FROM products_l2")
    suspend fun clearSecondProductsList()

    /**
     * @return A Flow that emits the list of GradeEntity from the database.
     * */
    @Query("SELECT * FROM grades")
    fun observeGrades(): Flow<List<GradeEntity>>

    /**
     * @param items - The list of GradeEntity to insert or update.
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGrades(items: List<GradeEntity>)

    /**
     * clears all entries in the grades table.
     * */
    @Query("DELETE FROM grades")
    suspend fun clearGrades()

    /**
     * @return The list of ProductL1Entity from the database.
     * */
    @Query("SELECT * FROM products_l1")
    suspend fun getFirstProductsList(): List<ProductL1Entity>

    /**
     * @return The list of ProductL2Entity from the database.
     * */
    @Query("SELECT * FROM products_l2")
    suspend fun getSecondProductsList(): List<ProductL2Entity>
}