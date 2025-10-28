package com.example.shopapplication.ui.util

import javax.inject.Inject
/**
 * Implementation of [TimeProvider] that returns the system time.
 * */
class SystemTimeProvider @Inject constructor() : TimeProvider {
    override fun now(): Long = System.currentTimeMillis()
}