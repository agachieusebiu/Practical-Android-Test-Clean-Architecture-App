package com.example.shopapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    /**
     * Provides the IO dispatcher for coroutine operations.
     * */
    @Provides @Named("io")
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}