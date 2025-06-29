package com.example.exchangerates.features.favorites.data.di

import com.example.exchangerates.features.favorites.abstractions.FavoritePairsRepository
import com.example.exchangerates.features.favorites.data.FavoritePairsRepositoryImpl
import com.example.exchangerates.features.favorites.data.db.FavoriteDataSource
import com.example.exchangerates.features.favorites.data.db.FavoriteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface FavoritesModule {
    @Binds
    @Singleton
    fun bindFavoritesDataSource(impl: FavoriteDataSourceImpl): FavoriteDataSource

    @Binds
    @Singleton
    fun bindFavoritesRepository(impl: FavoritePairsRepositoryImpl): FavoritePairsRepository
}