package com.example.exchangerates.features.favorites.abstractions

import com.example.exchangerates.features.favorites.entities.FavoritePair
import kotlinx.coroutines.flow.Flow

interface FavoritePairsRepository {
    fun getPairs(): Flow<List<FavoritePair>>
    suspend fun addPair(pair: FavoritePair)
    suspend fun removePair(baseCurrency: String, targetCurrency: String)
}