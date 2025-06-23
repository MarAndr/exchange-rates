package com.example.exchangerates.features.common.loading

sealed interface LoadingState<out T> {
    data object Loading: LoadingState<Nothing>

    // Might be a class with an ErrorReason
    data object Error : LoadingState<Nothing>

    data class Success<out T>(val data: T): LoadingState<T>
}