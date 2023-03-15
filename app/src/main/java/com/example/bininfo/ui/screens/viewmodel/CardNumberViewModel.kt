package com.example.bininfo.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bininfo.database.CardDao
import com.example.bininfo.database.CardData
import kotlinx.coroutines.flow.Flow

class CardNumberViewModel(private val cardDao: CardDao):ViewModel() {

    fun fullCardNumber(): Flow<List<CardData>> = cardDao.getAllNumber()
}
