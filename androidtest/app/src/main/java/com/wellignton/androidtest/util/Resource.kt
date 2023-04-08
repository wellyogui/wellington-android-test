package com.wellignton.androidtest.util

/**
 * Created by well_ on 08/04/2023 for Android test.
 */
sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
}
