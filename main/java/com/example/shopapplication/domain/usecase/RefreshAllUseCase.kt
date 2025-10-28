package com.example.shopapplication.domain.usecase

import com.example.shopapplication.data.repository.ProductRepository
import javax.inject.Inject

/**
 * Use case for refreshing all products.
 * @property repo The product repository to interact with.
 */
class RefreshAllUseCase @Inject constructor(
    private val repo: ProductRepository
) {
    /**
     * Invokes the use case to refresh all products.
     * @param force If true, forces a refresh even if data is up-to-date.
     */
    suspend operator fun invoke (force: Boolean): Unit = repo.refreshAll(force)
}