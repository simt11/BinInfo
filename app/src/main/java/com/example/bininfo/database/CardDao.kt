package com.example.bininfo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert
    fun insert(night: CardData)

    @Update
    fun update(night: CardData)

    @Query("SELECT * FROM carddata")
    fun getAllNumber(): Flow<List<CardData>>
}

