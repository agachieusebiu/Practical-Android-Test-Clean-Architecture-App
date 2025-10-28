package com.example.shopapplication.data.remote.dto

/**
 * Data Transfer Object representing a product received from a remote source.
 *
 * @property id The unique identifier for the product.
 * @property name The name of the product.
 * @property clientCount The number of clients associated with the product.
 */
data class ProductDto(
    val id: Int,
    val name: String,
    val clientCount: Int
)