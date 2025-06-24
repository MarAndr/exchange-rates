package com.example.exchangerates.features.favorites.impl

import com.example.exchangerates.features.favorites.api.FavoriteDataSource
import com.example.exchangerates.features.favorites.api.FavoritePairsRepository
import com.example.exchangerates.features.favorites.api.model.FavoritePair
import javax.inject.Inject

class FavoritePairsRepositoryImpl @Inject constructor(
    private val favoriteDataSource: FavoriteDataSource,
) : FavoritePairsRepository {
    override fun getPairs() = favoriteDataSource.getFavoritePairs()

    override suspend fun addPair(pair: FavoritePair) = favoriteDataSource.addFavoritePair(pair)

    override suspend fun removePair(pair: FavoritePair) = favoriteDataSource.removeFavoritePair(pair)
}