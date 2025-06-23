package com.example.exchangerates.features.rates.impl.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val symbol: String,
    val name: String,
    val isFavorite: Boolean
)