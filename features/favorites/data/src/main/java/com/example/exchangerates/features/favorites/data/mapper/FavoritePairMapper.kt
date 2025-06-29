package com.example.exchangerates.features.favorites.data.mapper

import com.example.exchangerates.features.favorites.entities.FavoritePair
import com.example.exchangerates.features.favorites.data.db.model.FavoritePairEntity

internal object FavoritePairMapper {
    
    fun mapToEntity(pair: FavoritePair): FavoritePairEntity {
        return FavoritePairEntity(
            id = pair.id,
            baseCurrency = pair.baseCurrency,
            targetCurrency = pair.targetCurrency
        )
    }
    
    private fun mapToDomain(entity: FavoritePairEntity): FavoritePair {
        return FavoritePair(
            id = entity.id,
            baseCurrency = entity.baseCurrency,
            targetCurrency = entity.targetCurrency
        )
    }
    
    fun mapToDomainList(entities: List<FavoritePairEntity>): List<FavoritePair> {
        return entities.map { mapToDomain(it) }
    }
} 