package com.example.shopapplication.di

import com.example.shopapplication.data.remote.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://restmock.plan-net.technology/mobile_test/"

    /**
     * Provides a singleton instance of OkHttpClient with logging interceptor.
     * @return The OkHttpClient instance.
     */
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    /**
     * Provides a singleton instance of Moshi for JSON serialization/deserialization.
     * @return The Moshi instance.
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Provides a singleton instance of Retrofit configured with the base URL, OkHttpClient, and Moshi converter.
     * @param client The OkHttpClient instance.
     * @param moshi The Moshi instance.
     * @return The Retrofit instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    /**
     * Provides a singleton instance of ApiService for making API calls.
     * @param retrofit The Retrofit instance.
     * @return The ApiService instance.
     */
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}