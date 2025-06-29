package com.example.exchangerates.features.favorites.data.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exchangerates.features.favorites.data.db.model.FavoritePairEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePairsDao {
    @Query("select * from FavoritePairEntity")
    fun getPairs(): Flow<List<FavoritePairEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPair(pair: FavoritePairEntity)

    @Query("DELETE FROM FAVORITEPAIRENTITY WHERE baseCurrency=:base AND targetCurrency=:target")
    fun removePair(base: String, target: String)
}