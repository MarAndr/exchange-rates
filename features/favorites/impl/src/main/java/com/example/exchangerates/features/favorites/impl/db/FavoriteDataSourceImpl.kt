package com.example.exchangerates.features.favorites.impl.db

import com.example.exchangerates.features.favorites.api.model.FavoritePair
import com.example.exchangerates.features.favorites.impl.db.model.FavoritePairEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FavoriteDataSourceImpl @Inject constructor(
    private val favoritePairsDao: FavoritePairsDao,
) : FavoriteDataSource {
    override fun getFavoritePairs() = favoritePairsDao.getPairs()
        .map { list ->
            // todo add mappers
            list.map { entity ->
                FavoritePair(
                    id = entity.id,
                    baseCurrency = entity.baseCurrency,
                    targetCurrency = entity.targetCurrency,
                )
            }
        }

    override suspend fun addFavoritePair(pair: FavoritePair) {
        val entity = FavoritePairEntity(
            baseCurrency = pair.baseCurrency,
            targetCurrency = pair.targetCurrency,
        )
        favoritePairsDao.addPair(entity)
    }

    override suspend fun removeFavoritePair(baseCurrency: String, targetCurrency: String) {
        favoritePairsDao.removePair(base = baseCurrency, target = targetCurrency)
    }
}