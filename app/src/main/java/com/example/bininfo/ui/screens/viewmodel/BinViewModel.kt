package com.example.bininfo.ui.screens.viewmodel

import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.database.AppDatabase
import com.example.bininfo.database.CardDao
import com.example.bininfo.network.BinApi
import com.example.bininfo.network.BinCard
import com.example.bininfo.utilits.intentCoordinates
import com.example.bininfo.utilits.intentPhone
import com.example.bininfo.utilits.intentUrl
import kotlinx.coroutines.launch

private const val MIN_LENGTH_CARD = 5

class BinViewModel() : ViewModel() {
    private lateinit var db: AppDatabase
    private lateinit var cardDao: CardDao
    private val _binUiStatus = MutableLiveData<BinCard>()
    val binUiStatus: LiveData<BinCard> = _binUiStatus
    private val _viewFlag = MutableLiveData<ViewFlag>()
    val viewFlag: LiveData<ViewFlag> = _viewFlag
    private val _intent = MutableLiveData<Intent>()
    val intent: LiveData<Intent> = _intent
    private lateinit var preferences: SharedPreferences
    private val BIN_NUMBER: String = ""
    private val _prefNumber = MutableLiveData<String>()
    val prefNumber: LiveData<String> = _prefNumber

    fun getBinInfo(bin: String) {
        if (bin.length < MIN_LENGTH_CARD) {
            _viewFlag.value = ViewFlag(false, false)
        } else {
            setBinStatus(bin)
            _viewFlag.value = ViewFlag(true, true)
        }
    }

    fun setBinStatus(bin: String) {
        viewModelScope.launch {
            val listResult = BinApi.retrofitService.getBinInfo(bin)
            _binUiStatus.value = listResult
        }
    }

    fun answerCoordinat(coordinates: String) {
        if (coordinates.isNotEmpty()) {
            _intent.value = intentCoordinates(coordinates)
        }
    }

    fun answerBankUrl(url: String) {
        if (url.isNotEmpty()) {
            _intent.value = intentUrl(url)
        }
    }

    fun answerBankPhone(phone: String) {
        if (phone.isNotEmpty()) {
            _intent.value = intentPhone(phone)
        }
    }

    fun prefOnPause(number: String) {
        //preferences = getSharedPreferences("BIN", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(BIN_NUMBER, number)
        editor.commit()
    }

    fun prefOnResume(number: String) {
        if (preferences.contains(BIN_NUMBER)) {
            if (number.isNullOrEmpty()) {
                val string = preferences.getString(BIN_NUMBER, "").toString()
                _prefNumber.value = string
            } else {
                _prefNumber.value = number
            }
        }
    }
}
