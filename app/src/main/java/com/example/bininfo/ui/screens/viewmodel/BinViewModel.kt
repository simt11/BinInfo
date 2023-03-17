package com.example.bininfo.ui.screens.viewmodel

import android.app.Activity
import android.provider.Settings.Global.getString
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.R
import com.example.bininfo.network.BinCard
import com.example.bininfo.network.BinApi
import com.example.bininfo.utilits.hideKeyboard
import com.example.bininfo.utilits.showToast
import com.google.android.material.internal.ViewUtils.hideKeyboard
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