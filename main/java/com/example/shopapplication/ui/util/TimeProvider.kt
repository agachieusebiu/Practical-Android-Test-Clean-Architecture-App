package com.example.shopapplication.ui.util

/**
 * A simple interface to provide the current time in milliseconds.
 * */
interface TimeProvider {
    fun now(): Long
}