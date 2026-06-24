package com.example.spiritguide.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spiritguide.model.Cocktail
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktails")
    fun getAllCocktails(): Flow<List<Cocktail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(cocktails: List<Cocktail>)
}