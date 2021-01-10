package com.example.tipee

import android.app.Application
import com.example.tipee.utils.SharedPreferencesUtils

class TipeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtils.context = applicationContext
    }
}