package com.example.shopapplication.data.repository

import com.example.shopapplication.data.local.dao.CacheMetaDao
import com.example.shopapplication.data.local.dao.ProductDao
import com.example.shopapplication.data.local.entity.CacheMetaEntity
import com.example.shopapplication.data.local.entity.GradeEntity
import com.example.shopapplication.data.local.entity.ProductL1Entity
import com.example.shopapplication.data.local.entity.ProductL2Entity
import com.example.shopapplication.data.remote.ApiService
import com.example.shopapplication.data.repository.ProductRepository.Companion.TTL_MS
import com.example.shopapplication.domain.model.Grade
import com.example.shopapplication.domain.model.Product
import com.example.shopapplication.ui.util.TimeProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

/**
 * UI classes & view models doesn't access data sources directly.
 * They use repository classes to get data.
 * */
class ProductRepository @Inject constructor(
    private val api: ApiService,
    private val productDao: ProductDao,
    private val cacheMetaDao: CacheMetaDao,
    private val time: TimeProvider,
    @param:Named("io") private val io: CoroutineDispatcher
) {

    /**
     * Listen the changes from products_l1 table in the database.
     * Convert the ProductL1Entity into domain models, named Product.
     * */
    fun observeFirstProductsList(): Flow<List<Product>> =
        productDao.observeFirstProductsList().map { list ->
            list.map { Product(it.id, it.name, it.clientCount) }
        }

    /**
     * Listen the changes from products_l2 table in the database.
     * Convert the ProductL2Entity into domain models, named Product.
     * */
    fun observeSecondProductsList(): Flow<List<Product>> =
        productDao.observeSecondProductsList().map { list ->
            list.map { Product(it.id, it.name, it.clientCount) }
        }

    /**
     * Listen the changes from grades table in the database.
     * Convert the GradeEntity into domain models, named Grade.
     * */
    fun observeGrades(): Flow<List<Grade>> = productDao.observeGrades().map { list ->
        list.map { Grade(it.productId, "${it.name} - ${it.grade}") }
    }

    /**
     * Refresh for all data (L1, L2, Grades) if the cached data is expired or [force] is true.
     * Using background [io] dispatcher for all operations.
     * */
    suspend fun refreshAll(force: Boolean = false): Unit = withContext(io) {
        val now = time.now()
        // Call the common method for both products lists
        refreshProductsListIfNeeded(now, force, true)
        refreshProductsListIfNeeded(now, force, false)
        // Tab3 depends on Tab1 and Tab2, so refresh it last
        refreshGradesParallelIfNeeded(now, force)
    }

    /**
     * Refresh the products list if the cached data is expired or [force] (from pull to refresh) is true.
     * @param isFirstProductsList true for first products list, false for second products list.
     * */
    private suspend fun refreshProductsListIfNeeded(
        now: Long,
        force: Boolean,
        isFirstProductsList: Boolean
    ) {
        if (force || isExpired(if(isFirstProductsList) FIRST_PRODUCTS_KEY else SECOND_PRODUCTS_KEY, now)) {
            val remote = if (isFirstProductsList) api.getProductsLevel1() else api.getProductsLevel2()
            productDao.run {
                if (isFirstProductsList) {
                    clearFirstProductsList()
                    updateFirstProductsList(remote.map {
                        ProductL1Entity(it.id, it.name, it.clientCount)
                    })
                } else {
                    clearSecondProductsList()
                    updateSecondProductsList(remote.map {
                        ProductL2Entity(it.id, it.name, it.clientCount)
                    })
                }
            }
            cacheMetaDao.update(CacheMetaEntity(if(isFirstProductsList) FIRST_PRODUCTS_KEY else SECOND_PRODUCTS_KEY, now))
        }
    }

    /**
     * Refresh the grades if the cached data is expired or [force] (from pull to refresh) is true.
     * This function fetches the L1 and L2 products from the database to get the product IDs,
     * then fetches the grades for each product in parallel using coroutines.
     * Finally, it updates the grades in the database and updates the cache meta.
     * */
    private suspend fun refreshGradesParallelIfNeeded(now: Long, force: Boolean) = withContext(io) {
        if (force || isExpired("grades", now)) {
            refreshGradesParallelIfNeededBody(now)
        }
    }

    private suspend fun refreshGradesParallelIfNeededBody(now: Long) = withContext(io) {
        // Get L1 and L2 products from the database
        val firstProductsList = productDao.getFirstProductsList()
        val secondProductsList = productDao.getSecondProductsList()
        // Combine the IDs from both lists into a set to avoid duplicates
        val ids = (firstProductsList.map { it.id } + secondProductsList.map { it.id }.toSet())

        // Create maps for quick lookup by ID
        val firstProductsMap = firstProductsList.associateBy({ it.id }, { it })
        val secondProductsMap = secondProductsList.associateBy({ it.id }, { it })

        val gradeEntities = coroutineScope {
            ids.map { id ->
                async {
                    // Use the name from L1 if available, otherwise from L2, otherwise use "NO NAME"
                    val name = (firstProductsMap[id]?.name) ?: (secondProductsMap[id]?.name) ?: NO_NAME
                    // Get client counts, defaulting to 0 if the product is not found
                    val firstClientCount = firstProductsMap[id]?.clientCount ?: EMPTY_CLIENT_COUNT
                    val secondClientCount = secondProductsMap[id]?.clientCount ?: EMPTY_CLIENT_COUNT
                    // Fetch the grade from the API
                    val dto = api.getGrade(id, firstClientCount, secondClientCount)
                    GradeEntity(
                        productId = dto.productId,
                        name = dto.name.ifBlank { name },
                        grade = dto.grade
                    )
                }
            }.awaitAll() // Await all async operations to complete and collect results
        }

        // Clear old grades and insert new ones
        productDao.clearGrades()
        productDao.updateGrades(gradeEntities)
        // Update the cache meta with the current time
        cacheMetaDao.update(CacheMetaEntity("grades", now))
    }

    /**
     * If there is no cache record for the given [key], consider it as expired.
     * If the difference between [now] and the last updated time is greater than
     * [TTL_MS], consider it as expired.
     * @return true if the cache for the given [key] is expired.
     * */
    private suspend fun isExpired(key: String, now: Long): Boolean =
        cacheMetaDao.lastUpdated(key)?.let { now - it > TTL_MS } ?: true

    companion object {
        const val TTL_MS: Long = 5 * 60 * 1000L // 5 minutes
        private const val NO_NAME = "No available name"
        private const val EMPTY_CLIENT_COUNT = 0
        private const val FIRST_PRODUCTS_KEY = "l1"
        private const val SECOND_PRODUCTS_KEY = "l2"
    }
}