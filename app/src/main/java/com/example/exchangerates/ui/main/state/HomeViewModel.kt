package com.example.exchangerates.ui.main.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val selectedTab = MutableStateFlow(HomeTab.Rates)

    val screenState = selectedTab
        .map { selectedTab ->
            HomeScreenState(
                selectedTab = selectedTab,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = HomeScreenState(HomeTab.Rates)
        )

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.TabSelected -> {
                selectedTab.value = event.tab
            }
        }
    }
}