package com.example.exchangerates.domain.mapper

import com.example.exchangerates.domain.model.Symbol
import com.example.exchangeratestestapppublic.db.model.CurrencyDbModel
import com.example.exchangerates.domain.model.NameModel
import javax.inject.Inject

class NamesDbMapper @Inject constructor() {

    fun mapToDomain(currencyDb: CurrencyDbModel) = NameModel(
        id = currencyDb.id,
        symbol = Symbol.valueOf(currencyDb.symbol),
        name = currencyDb.name,
    )

    fun mapToDb(symbol: Symbol, name: String) = CurrencyDbModel(
        id = 0,
        symbol = symbol.toString(),
        name = name,
        isFavorite = false
    )
}