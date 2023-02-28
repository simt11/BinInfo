package com.example.bininfo.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.network.BinApi
import kotlinx.coroutines.launch

class BinViewModel() : ViewModel() {
    var binUiStatus: String by mutableStateOf("")
        private set

    fun getBinInfo(BIN: Int) {
        viewModelScope.launch {
            val listResult = BinApi.retrofitService.getBinInfo(BIN.toString())
            binUiStatus = listResult
        }
    }
}