package com.example.shopapplication.ui.common

import com.example.shopapplication.domain.model.Product

/**
 * Represents the UI state for product entries.
 * Same structure as UiGradeEntryState but for products.
 */
sealed interface UiProductState {
    object Loading : UiProductState
    data class Success(val data: List<Product>) : UiProductState
    data class Error(val message: String) : UiProductState
}