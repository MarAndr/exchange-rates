package com.example.exchangerates.features.favorites.impl.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritePairEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val baseCurrency: String,
    val targetCurrency: String,
)