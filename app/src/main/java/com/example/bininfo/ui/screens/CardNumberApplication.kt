package com.example.bininfo.ui.screens

import android.app.Application
import com.example.bininfo.database.AppDatabase

class CardNumberApplication() : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}