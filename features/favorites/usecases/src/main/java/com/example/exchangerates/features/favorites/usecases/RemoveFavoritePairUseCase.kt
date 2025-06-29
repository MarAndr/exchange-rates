package com.example.exchangerates.features.favorites.usecases

import com.example.exchangerates.features.favorites.api.FavoritePairsRepository
import javax.inject.Inject

class RemoveFavoritePairUseCase @Inject constructor(
    private val favoritePairsRepository: FavoritePairsRepository,
) {
    suspend operator fun invoke(baseCurrency: String, targetCurrency: String) =
        favoritePairsRepository.removePair(baseCurrency, targetCurrency)
}