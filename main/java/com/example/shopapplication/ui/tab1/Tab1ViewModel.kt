package com.example.shopapplication.ui.tab1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapplication.data.repository.ProductRepository
import com.example.shopapplication.domain.usecase.RefreshAllUseCase
import com.example.shopapplication.ui.common.UiProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Tab1ViewModel @Inject constructor(
    private val repo: ProductRepository,
    private val refreshUseCase: RefreshAllUseCase
) : ViewModel() {

    /**
     * Use StateFlow to represent the UI state
     * */
    private val _items = MutableStateFlow<UiProductState>(UiProductState.Loading)
    val items: StateFlow<UiProductState> = _items

    init {
        /** Observe the products from the repository */
        viewModelScope.launch {
            repo.observeFirstProductsList().collect { products ->
                _items.value = if (products.isEmpty()) {
                    onPullToRefresh() // Initial load
                    UiProductState.Loading
                } else {
                    UiProductState.Success(products)
                }
            }
        }
    }

    /** Handle pull-to-refresh action */
    fun onPullToRefresh() {
        viewModelScope.launch {
            /** Show loading state */
            _items.value = UiProductState.Loading // Show loading state
            try {
                refreshUseCase(force = true)
            } catch (e: Exception) {
                _items.value = UiProductState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}

