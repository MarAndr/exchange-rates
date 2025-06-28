package com.example.exchangerates.core.db.di

import android.app.Application
import androidx.room.Room
import com.example.exchangerates.core.db.AppDatabase
import com.example.exchangerates.features.favorites.impl.db.FavoritePairsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Application): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoritesPairDao(db: AppDatabase): FavoritePairsDao {
        return db.favoritePairsDao()
    }
}
