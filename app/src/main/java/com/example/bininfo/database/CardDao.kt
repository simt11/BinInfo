package com.example.bininfo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCardData(cardData: CardData)

    @Update
    fun update(cardData: CardData)

    @Query("SELECT * FROM carddata")
    fun getAllNumber(): Flow<List<CardData>>
}

