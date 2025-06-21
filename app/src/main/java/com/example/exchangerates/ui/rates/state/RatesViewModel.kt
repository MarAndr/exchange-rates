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

    private val _screenState = MutableStateFlow<RatesScreenState>(RatesScreenState.Loading)
    val screenState: StateFlow<RatesScreenState> = _screenState

    private val _baseCurrency = MutableStateFlow("USD")
    val baseCurrency: StateFlow<String> = _baseCurrency

    init {
        loadCurrencies()
    }

    private fun onBaseCurrencyChanged(newBaseCurrency: String) {
        if (_baseCurrency.value != newBaseCurrency) {
            _baseCurrency.value = newBaseCurrency
            loadCurrencies()
        }
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            _screenState.value = RatesScreenState.Loading

            try {
                val response = repository.getLatestCurrencies(_baseCurrency.value)
                _screenState.value = RatesScreenState.Success(currencies = response)
            } catch (e: Exception) {
                _screenState.value = RatesScreenState.Error(e.message.toString())
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