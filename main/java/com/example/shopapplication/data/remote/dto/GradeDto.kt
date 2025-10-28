package com.example.shopapplication.data.remote.dto

/**
 * Data Transfer Object representing a product grade received from a remote source.
 *
 * @property productId The unique identifier for the product.
 * @property name The name of the product.
 * @property grade The grade assigned to the product.
 */
data class GradeDto(
    val productId: Int,
    val name: String,
    val grade: String
)