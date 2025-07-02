package com.example.exchangerates.features.favorites.data.mapper

import com.example.exchangerates.features.favorites.entities.FavoritePair
import com.example.exchangerates.features.favorites.data.db.model.FavoritePairEntity
import com.example.exchangerates.features.rates.entities.CurrencySymbol

internal object FavoritePairMapper {
    
    fun mapToEntity(pair: FavoritePair): FavoritePairEntity {
        return FavoritePairEntity(
            id = pair.id,
            baseCurrency = pair.baseCurrency.value,
            targetCurrency = pair.targetCurrency.value
        )
    }
    
    private fun mapToDomain(entity: FavoritePairEntity): FavoritePair {
        return FavoritePair(
            id = entity.id,
            baseCurrency = CurrencySymbol(entity.baseCurrency),
            targetCurrency = CurrencySymbol(entity.targetCurrency)
        )
    }
    
    fun mapToDomainList(entities: List<FavoritePairEntity>): List<FavoritePair> {
        return entities.map { mapToDomain(it) }
    }
} 