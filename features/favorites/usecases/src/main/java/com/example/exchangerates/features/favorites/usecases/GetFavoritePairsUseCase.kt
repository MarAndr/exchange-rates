package com.example.exchangerates.features.favorites.usecases

import com.example.exchangerates.features.favorites.api.FavoritePairsRepository
import com.example.exchangerates.features.favorites.api.model.FavoritePair
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritePairsUseCase @Inject constructor(
    private val favoritePairsRepository: FavoritePairsRepository,
) {
    operator fun invoke(): Flow<List<FavoritePair>> =
        favoritePairsRepository.getPairs()
}