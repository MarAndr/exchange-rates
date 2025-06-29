package com.example.exchangerates.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exchangerates.features.favorites.data.db.FavoritePairsDao
import com.example.exchangerates.features.favorites.data.db.model.FavoritePairEntity

@Database(
    entities = [FavoritePairEntity::class],
    version = AppDatabase.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritePairsDao(): FavoritePairsDao

    companion object {
        const val DB_NAME = "app_db"
        const val DB_VERSION = 1
    }
}