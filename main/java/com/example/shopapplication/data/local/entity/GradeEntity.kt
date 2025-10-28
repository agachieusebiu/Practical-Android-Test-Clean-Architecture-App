package com.example.shopapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a product grade in the local database.
 *
 * @property productId The unique identifier for the product.
 * @property name The name of the product.
 * @property grade The grade assigned to the product.
 */
@Entity(tableName = "grades")
data class GradeEntity(
    @PrimaryKey val productId: Int,
    val name: String,
    val grade: String
)