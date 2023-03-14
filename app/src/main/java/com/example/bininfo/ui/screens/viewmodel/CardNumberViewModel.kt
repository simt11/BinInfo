package com.example.bininfo.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bininfo.database.CardDao
import com.example.bininfo.database.CardData
import kotlinx.coroutines.flow.Flow

class CardNumberViewModel(private val cardDao: CardDao):ViewModel() {

    fun fullCardNumber(): Flow<List<CardData>> = cardDao.getAllNumber()
}

class CardNumberViewModelFactory(
    private val cardDao: CardDao,
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardNumberViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CardNumberViewModel(cardDao) as T
        }
         throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}