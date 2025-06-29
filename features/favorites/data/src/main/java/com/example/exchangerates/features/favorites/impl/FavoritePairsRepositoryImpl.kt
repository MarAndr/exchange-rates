package com.example.exchangerates.features.favorites.impl

import com.example.exchangerates.features.favorites.api.FavoritePairsRepository
import com.example.exchangerates.features.favorites.api.model.FavoritePair
import com.example.exchangerates.features.favorites.impl.db.FavoriteDataSource
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