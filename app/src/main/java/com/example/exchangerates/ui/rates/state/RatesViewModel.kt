package com.example.exchangerates.ui.rates.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor() : ViewModel() {

    val screenState = flowOf(RatesScreenState.Loading)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = RatesScreenState.Loading,
        )

    fun onEvent(event: RatesScreenEvent) {
        // todo
    }
}