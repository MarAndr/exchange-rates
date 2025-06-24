package com.example.exchangerates.features.favorites.impl.di

import com.example.exchangerates.features.favorites.api.FavoriteDataSource
import com.example.exchangerates.features.favorites.api.FavoritePairsRepository
import com.example.exchangerates.features.favorites.impl.FavoritePairsRepositoryImpl
import com.example.exchangerates.features.favorites.impl.db.FavoriteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesModule {
    @Binds
    @Singleton
    fun bindFavoritesDataSource(impl: FavoriteDataSourceImpl): FavoriteDataSource

    @Binds
    @Singleton
    fun bindFavoritesRepository(impl: FavoritePairsRepositoryImpl): FavoritePairsRepository
}