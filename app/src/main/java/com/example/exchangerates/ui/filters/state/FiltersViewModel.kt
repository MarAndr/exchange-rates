package com.example.exchangerates.ui.filters.state

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.exchangerates.ui.common.navigation.AppNavigator
import com.example.exchangerates.ui.common.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val screenArgs = savedStateHandle.toRoute<Destination.Filters>()

    private val sortOption = MutableStateFlow(screenArgs.selectedSorting)

    val screenState = sortOption.map { sortOption ->
        FiltersScreenState(
            sortOption = sortOption,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        FiltersScreenState(screenArgs.selectedSorting)
    )

    fun onEvent(event: FiltersScreenEvent) {
        when (event) {
            FiltersScreenEvent.OnBackClicked -> {
                appNavigator.back()
            }

            is FiltersScreenEvent.OnApply -> {
                appNavigator.backWithResult(event.sortOption)
            }

            is FiltersScreenEvent.SortOptionSelected -> {
                sortOption.value = event.option
            }

        }
    }
}