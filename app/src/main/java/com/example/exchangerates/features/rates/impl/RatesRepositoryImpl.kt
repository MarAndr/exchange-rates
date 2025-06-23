package com.example.exchangerates.features.rates.impl

import com.example.exchangerates.features.rates.impl.remote.ExchangeApi
import com.example.exchangerates.features.common.loading.LoadingState
import com.example.exchangerates.features.rates.api.RatesRepository
import com.example.exchangerates.features.rates.api.model.RatesItem
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val exchangeApi: ExchangeApi,
) : RatesRepository {
    override suspend fun getLatestRates(baseCurrency: String) = apiCall(
        mapper = { rates ->
            rates.rates.map { (symbol, rate) ->
                RatesItem(
                    symbol = symbol,
                    rate = rate,
                    isFavorite = false, // todo
                )
            }
        },
        call = { exchangeApi.getLatestCurrency(baseCurrency, null) },
    )

    private fun <ApiModel, DomainModel> apiCall(
        mapper: (ApiModel) -> DomainModel,
        call: suspend () -> ApiModel,
    ) = flow {
        emit(LoadingState.Loading)
        val result = try {
            val apiModel = call()
            val mapped = mapper(apiModel)
            LoadingState.Success(mapped)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadingState.Error
        }
        emit(result)
    }
}


