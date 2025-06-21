package com.example.exchangerates.domain

import com.example.exchangerates.api.ExchangeApi
import com.example.exchangerates.db.CurrenciesListDao
import com.example.exchangerates.domain.model.Symbol
import com.example.exchangerates.domain.mapper.NamesDbMapper
import com.example.exchangerates.domain.model.NameModel
import com.example.exchangerates.domain.model.RatesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExchangeRepository @Inject constructor(
    private val retrofit: ExchangeApi,
    private val currenciesListDao: CurrenciesListDao,
    private val namesDbMapper: NamesDbMapper
) {

    suspend fun fetchCurrencyNamesList(): List<NameModel> {
        val response = retrofit.getCurrencyNamesList()

        if (response.symbols == null) {
            throw Exception("Symbols are null")
        }

        val dbModels = response.symbols.map { (symbol, name) ->
            namesDbMapper.mapToDb(symbol = symbol, name = name)
        }

        return withContext(Dispatchers.IO) {
            currenciesListDao.addCurrenciesList(dbModels)
            currenciesListDao.getCurrenciesList().map {
                namesDbMapper.mapToDomain(it)
            }
        }
    }

    suspend fun fetchLatestCurrency(base: Symbol): List<RatesModel> {
        val response = retrofit.getLatestCurrency(
            base = base.value,
            symbols = Symbol.values().joinToString(",") { it.value }
        )

        if (response.rates == null) {
            throw Exception("No rates")
        }

        return response.rates
            .map {
                RatesModel(
                    base = base,
                    it.key,
                    it.value,
                )
            }
    }

    suspend fun setFavorite(nameModel: NameModel, isFavourite: Boolean) {
        currenciesListDao.setFavorite(
            namesDbMapper.mapToDb(
                nameModel.symbol, nameModel.name
            )
        )
    }

    fun setFavourite() {
        TODO("Not yet implemented")
    }
}
