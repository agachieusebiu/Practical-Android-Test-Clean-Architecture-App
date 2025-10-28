package com.example.shopapplication.data.remote

import com.example.shopapplication.data.remote.dto.GradeDto
import com.example.shopapplication.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * @return List of products at level 1 from the remote source, using the specified endpoint.
     * */
    @GET("getProductsLevel1.php")
    suspend fun getProductsLevel1(): List<ProductDto>

    /**
     * @return List of products at level 2 from the remote source, using the specified endpoint.
     * */
    @GET("getProductsLevel2.php")
    suspend fun getProductsLevel2(): List<ProductDto>

    /**
     * Fetches the grade for a specific product based on its ID and number of clients on both levels.
     *
     * @param productId The unique identifier of the product.
     * @param clientCountL1 The number of clients from first products list.
     * @param clientCountL2 The number of clients from second products list.
     * @return A [GradeDto] containing the product's grade information.
     */
    @GET("getGrade.php")
    suspend fun getGrade(
        @Query("productId") productId: Int,
        @Query("clientCountLevel1") clientCountL1: Int,
        @Query("clientCountLevel2") clientCountL2: Int
    ): GradeDto
}