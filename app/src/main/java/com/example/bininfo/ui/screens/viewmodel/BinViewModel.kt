package com.example.bininfo.ui.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.network.BinCard
import com.example.bininfo.network.BinApi
import kotlinx.coroutines.launch

class BinViewModel() : ViewModel() {
    private val _binUiStatus = MutableLiveData<BinCard>()
    val binUiStatus: LiveData<BinCard> = _binUiStatus

    fun getBinInfo(BIN: String) {
        viewModelScope.launch {
            val listResult = BinApi.retrofitService.getBinInfo(BIN)
            _binUiStatus.value = listResult
        }
    }
}