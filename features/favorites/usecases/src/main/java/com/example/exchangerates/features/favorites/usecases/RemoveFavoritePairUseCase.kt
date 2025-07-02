package com.example.exchangerates.features.favorites.usecases

import com.example.exchangerates.features.favorites.abstractions.FavoritePairsRepository
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import javax.inject.Inject

class RemoveFavoritePairUseCase @Inject constructor(
    private val favoritePairsRepository: FavoritePairsRepository,
) {
    suspend operator fun invoke(baseCurrency: CurrencySymbol, targetCurrency: CurrencySymbol) =
        favoritePairsRepository.removePair(baseCurrency.value, targetCurrency.value)
}