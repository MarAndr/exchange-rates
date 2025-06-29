package com.example.exchangerates.features.favorites.data.db

import com.example.exchangerates.features.favorites.entities.FavoritePair
import com.example.exchangerates.features.favorites.data.mapper.FavoritePairMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FavoriteDataSourceImpl @Inject constructor(
    private val favoritePairsDao: FavoritePairsDao,
) : FavoriteDataSource {
    override fun getFavoritePairs() = favoritePairsDao.getPairs()
        .map { list ->
            FavoritePairMapper.mapToDomainList(list)
        }

    override suspend fun addFavoritePair(pair: FavoritePair) {
        val entity = FavoritePairMapper.mapToEntity(pair)
        favoritePairsDao.addPair(entity)
    }

    override suspend fun removeFavoritePair(baseCurrency: String, targetCurrency: String) {
        favoritePairsDao.removePair(base = baseCurrency, target = targetCurrency)
    }
}