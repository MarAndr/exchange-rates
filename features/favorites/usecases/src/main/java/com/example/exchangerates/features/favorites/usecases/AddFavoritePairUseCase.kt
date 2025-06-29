package com.example.exchangerates.features.favorites.usecases

import com.example.exchangerates.features.favorites.abstractions.FavoritePairsRepository
import com.example.exchangerates.features.favorites.entities.FavoritePair
import javax.inject.Inject

class AddFavoritePairUseCase @Inject constructor(
    private val favoritePairsRepository: FavoritePairsRepository,
) {
    suspend operator fun invoke(pair: FavoritePair) =
        favoritePairsRepository.addPair(pair)
}