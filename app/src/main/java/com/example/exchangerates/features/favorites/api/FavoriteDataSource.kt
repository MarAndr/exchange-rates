package com.example.exchangerates.features.favorites.api

import com.example.exchangerates.features.favorites.api.model.FavoritePair
import com.example.exchangerates.features.favorites.impl.db.model.FavoritePairEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteDataSource {

    fun getFavoritePairs(): Flow<List<FavoritePair>>
    suspend fun addFavoritePair(pair: FavoritePair)
    suspend fun removeFavoritePair(pair: FavoritePair)
}