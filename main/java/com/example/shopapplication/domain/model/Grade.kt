package com.example.shopapplication.domain.model

/**
 * Domain model representing a product grade.
 * @property productId The unique identifier for the product.
 * @property display The display string for the product grade.
 */
data class Grade(
    val productId: Int,
    val display: String
)