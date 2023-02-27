package com.example.bininfo.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.network.BinApi
import kotlinx.coroutines.launch

class BinViewModel(BIN: Int) : ViewModel() {
    var binUiStatus: String by mutableStateOf("")

    init {
        getBinInfo(BIN)
    }

    fun getBinInfo(BIN: Int) {
        viewModelScope.launch {
            val listResult = BinApi.retrofitService.getBinInfo(BIN.toString())
            binUiStatus = listResult
        }
    }
}