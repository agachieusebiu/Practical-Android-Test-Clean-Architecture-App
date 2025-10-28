package com.example.shopapplication.domain.model

/**
 * Domain model representing a product.
 * @property id The unique identifier for the product.
 * @property name The name of the product.
 * @property clientCount The number of clients associated with the product.
 */
data class Product(
    val id: Int,
    val name: String,
    val clientCount: Int
)