package com.example.exchangerates.features.favorites.usecases

import com.example.exchangerates.features.favorites.api.FavoritePairsRepository
import com.example.exchangerates.features.favorites.api.model.FavoritePair
import javax.inject.Inject

class AddFavoritePairUseCase @Inject constructor(
    private val favoritePairsRepository: FavoritePairsRepository,
) {
    suspend operator fun invoke(pair: FavoritePair) =
        favoritePairsRepository.addPair(pair)
}