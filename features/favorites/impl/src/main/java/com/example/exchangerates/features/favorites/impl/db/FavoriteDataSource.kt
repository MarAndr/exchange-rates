package com.example.exchangerates.features.favorites.impl.db

import com.example.exchangerates.features.favorites.api.model.FavoritePair
import kotlinx.coroutines.flow.Flow

internal interface FavoriteDataSource {
    fun getFavoritePairs(): Flow<List<FavoritePair>>
    suspend fun addFavoritePair(pair: FavoritePair)
    suspend fun removeFavoritePair(baseCurrency: String, targetCurrency: String)
}