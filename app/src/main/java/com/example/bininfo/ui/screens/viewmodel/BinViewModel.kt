package com.example.bininfo.ui.screens.viewmodel

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.R
import com.example.bininfo.database.AppDatabase
import com.example.bininfo.database.CardData
import com.example.bininfo.network.BinApi
import com.example.bininfo.network.BinCard
import com.example.bininfo.utilits.showToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val MIN_LENGTH_CARD = 4

class BinViewModel(private val context: Context) : ViewModel() {
    private val _binUiStatus = MutableLiveData<BinCard>()
    val binUiStatus: LiveData<BinCard> = _binUiStatus
    private val cardDao = db.cardDao()

    fun getBinInfo(BIN: String, db: AppDatabase) {
        if (BIN.length <= MIN_LENGTH_CARD) {
            showToast(context, context.getString(R.string.toast_min_number))
        } else {
            saveCardNumber(BIN)
            viewModelScope.launch {
                val listResult = BinApi.retrofitService.getBinInfo(BIN)
                _binUiStatus.value = listResult
            }
        }
    }

    private fun saveCardNumber(number: String) {
        val cardData = CardData(cardNumber = number)
        GlobalScope.launch {
            cardDao.insertCardData(cardData)
        }
    }
}