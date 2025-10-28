package com.example.shopapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a Level 2 (Second) Product in the local database.
 *
 * @property id The unique identifier for the product.
 * @property name The name of the product.
 * @property clientCount The number of clients associated with the product.
 */
@Entity(tableName = "products_l2")
data class ProductL2Entity(
    @PrimaryKey val id: Int,
    val name: String,
    val clientCount: Int
)