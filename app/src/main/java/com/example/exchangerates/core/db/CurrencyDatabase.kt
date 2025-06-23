package com.example.exchangerates.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exchangerates.features.rates.impl.db.CurrenciesListDao
import com.example.exchangerates.features.rates.impl.db.CurrencyDbModel

@Database(
    entities = [CurrencyDbModel::class],
    version = CurrencyDatabase.DB_VERSION
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currenciesListDao(): CurrenciesListDao

    companion object {
        const val DB_NAME = "currencyDatabase"
        const val DB_VERSION = 2
    }
}