package com.example.exchangerates.features.favorites.api

import com.example.exchangerates.features.favorites.api.model.FavoritePair
import kotlinx.coroutines.flow.Flow

interface FavoritePairsRepository {
    fun getPairs(): Flow<List<FavoritePair>>
    suspend fun addPair(pair: FavoritePair)
    suspend fun removePair(pair: FavoritePair)
}