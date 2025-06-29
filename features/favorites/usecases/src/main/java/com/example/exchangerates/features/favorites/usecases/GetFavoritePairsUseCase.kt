package com.example.exchangerates.features.favorites.usecases

import com.example.exchangerates.features.favorites.abstractions.FavoritePairsRepository
import com.example.exchangerates.features.favorites.entities.FavoritePair
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritePairsUseCase @Inject constructor(
    private val favoritePairsRepository: FavoritePairsRepository,
) {
    operator fun invoke(): Flow<List<FavoritePair>> =
        favoritePairsRepository.getPairs()
}