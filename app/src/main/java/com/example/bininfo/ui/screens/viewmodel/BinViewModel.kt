package com.example.bininfo.ui.screens.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.network.BinApi
import com.example.bininfo.network.BinCard
import com.example.bininfo.utilits.intentCoordinates
import com.example.bininfo.utilits.intentPhone
import com.example.bininfo.utilits.intentUrl
import kotlinx.coroutines.launch

private const val MIN_LENGTH_CARD = 5

class BinViewModel() : ViewModel() {
    private val _binUiStatus = MutableLiveData<BinCard>()
    val binUiStatus: LiveData<BinCard> = _binUiStatus
    private val _viewFlag = MutableLiveData<ViewFlag>()
    val viewFlag: LiveData<ViewFlag> = _viewFlag
    private val _intent = MutableLiveData<Intent>()
    val intent: LiveData<Intent> = _intent

    fun getBinInfo(bin: String) {
        if (bin.length < MIN_LENGTH_CARD) {
            _viewFlag.value = ViewFlag(false,  false, "")
        } else {
            setBinStatus(bin)
            _viewFlag.value = ViewFlag(true, true, bin)
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
}
