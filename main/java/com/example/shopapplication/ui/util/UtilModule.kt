package com.example.shopapplication.ui.util

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module to provide the [TimeProvider] implementation.
 * */
@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Binds
    @Singleton
    abstract fun bindTimeProvider(
        impl: SystemTimeProvider
    ): TimeProvider
}