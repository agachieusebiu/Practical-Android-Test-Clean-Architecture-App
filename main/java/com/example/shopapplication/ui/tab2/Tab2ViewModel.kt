package com.example.shopapplication.ui.tab2

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

/**
 * Same logic as Tab1ViewModel
 * */
@HiltViewModel
class Tab2ViewModel @Inject constructor(
    private val repo: ProductRepository,
    private val refreshUseCase: RefreshAllUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<UiProductState>(UiProductState.Loading)
    val items: StateFlow<UiProductState> = _items

    init {
        viewModelScope.launch {
            repo.observeSecondProductsList().collect { products ->
                _items.value = if (products.isEmpty()) {
                    onPullToRefresh() // Initial load
                    UiProductState.Loading
                } else {
                    UiProductState.Success(products)
                }
            }
        }
    }

    fun onPullToRefresh() {
        viewModelScope.launch {
            _items.value = UiProductState.Loading
            try {
                refreshUseCase(force = true)
            } catch (e: Exception) {
                _items.value = UiProductState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}

