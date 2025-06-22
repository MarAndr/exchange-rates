package com.example.exchangerates.ui.rates.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.data.RatesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val repository: RatesRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<RatesScreenState>(RatesScreenState.Loading("USD"))
    val screenState: StateFlow<RatesScreenState> = _screenState

    init {
        loadCurrencies()
    }

    private fun onBaseCurrencyChanged(newBaseCurrency: String) {
        val currentState = _screenState.value
        if (getBaseCurrency(currentState) != newBaseCurrency) {
            loadCurrencies(newBaseCurrency)
        }
    }

    private fun getBaseCurrency(state: RatesScreenState): String {
        return when (state) {
            is RatesScreenState.Loading -> state.baseCurrency
            is RatesScreenState.Success -> state.baseCurrency
            is RatesScreenState.Error -> state.baseCurrency
        }
    }

    private fun loadCurrencies(baseCurrency: String = "USD") {
        viewModelScope.launch {
            _screenState.value = RatesScreenState.Loading(baseCurrency)

            try {
                val response = repository.getLatestCurrencies(baseCurrency)
                _screenState.value = RatesScreenState.Success(
                    baseCurrency = baseCurrency,
                    currencies = response
                )
            } catch (e: Exception) {
                _screenState.value = RatesScreenState.Error(
                    baseCurrency = baseCurrency,
                    message = e.message.toString()
                )
            }
        }
    }

    fun onEvent(event: RatesScreenEvent) {
        when (event) {
            is RatesScreenEvent.OnFavoriteClick -> {
                // TODO: Implement favorite toggle
            }
            is RatesScreenEvent.OnScreenLoad -> {
                loadCurrencies()
            }
            is RatesScreenEvent.OnRefresh -> {
                loadCurrencies()
            }
            is RatesScreenEvent.OnBaseCurrencyChanged -> {
                onBaseCurrencyChanged(event.newBaseCurrency)
            }
        }
    }
}