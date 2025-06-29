package com.example.exchangerates.ui.common.state

import kotlinx.coroutines.flow.Flow

interface Refreshable<T> {

    val flow: Flow<RefreshLoadingState<T>>

    fun refresh()
}