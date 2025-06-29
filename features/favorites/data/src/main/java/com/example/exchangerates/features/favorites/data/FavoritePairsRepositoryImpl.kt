package com.example.exchangerates.features.favorites.data

import com.example.exchangerates.features.favorites.abstractions.FavoritePairsRepository
import com.example.exchangerates.features.favorites.entities.FavoritePair
import com.example.exchangerates.features.favorites.data.db.FavoriteDataSource
import javax.inject.Inject

internal class FavoritePairsRepositoryImpl @Inject constructor(
    private val favoriteDataSource: FavoriteDataSource,
) : FavoritePairsRepository {
    override fun getPairs() = favoriteDataSource.getFavoritePairs()

    override suspend fun addPair(pair: FavoritePair) = favoriteDataSource.addFavoritePair(pair)

    override suspend fun removePair(baseCurrency: String, targetCurrency: String) =
        favoriteDataSource.removeFavoritePair(
            baseCurrency = baseCurrency,
            targetCurrency = targetCurrency,
        )
}