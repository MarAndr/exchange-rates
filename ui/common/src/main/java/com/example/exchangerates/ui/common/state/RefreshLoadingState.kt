package com.example.exchangerates.ui.common.state

sealed interface RefreshLoadingState<out T> {

    sealed interface Initial : RefreshLoadingState<Nothing> {

        data object Loading : Initial

        data object Error : Initial
    }

    data class Data<T>(
        val data: T,
        val isError: Boolean,
        val isLoading: Boolean,
    ) : RefreshLoadingState<T>
}