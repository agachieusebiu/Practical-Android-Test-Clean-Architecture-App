package com.example.shopapplication.ui.common

import com.example.shopapplication.domain.model.Grade

/**
 * Represents the UI state for grade entries.
 */
sealed interface UiGradeEntryState {
    // Represents the loading state.
    object Loading : UiGradeEntryState

    // Represents the success state with a list of grades.
    data class Success(val data: List<Grade>) : UiGradeEntryState

    // Represents the error state with an error message.
    data class Error(val message: String) : UiGradeEntryState
}