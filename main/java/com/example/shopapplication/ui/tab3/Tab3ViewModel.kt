package com.example.shopapplication.ui.tab3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapplication.data.repository.ProductRepository
import com.example.shopapplication.domain.usecase.RefreshAllUseCase
import com.example.shopapplication.ui.common.UiGradeEntryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Same logic as Tab1ViewModel
 * */
@HiltViewModel
class Tab3ViewModel @Inject constructor(
    private val repo: ProductRepository,
    private val refreshUseCase: RefreshAllUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<UiGradeEntryState>(UiGradeEntryState.Loading)
    val items: StateFlow<UiGradeEntryState> = _items

    init {
        viewModelScope.launch {
            repo.observeGrades().collect { grades ->
                _items.value = if (grades.isEmpty()) {
                    onPullToRefresh() // Initial load
                    UiGradeEntryState.Loading
                } else {
                    UiGradeEntryState.Success(grades)
                }
            }
        }
    }

    fun onPullToRefresh() {
        viewModelScope.launch {
            _items.value = UiGradeEntryState.Loading
            try {
                refreshUseCase(force = true)
            } catch (e: Exception) {
                _items.value = UiGradeEntryState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
