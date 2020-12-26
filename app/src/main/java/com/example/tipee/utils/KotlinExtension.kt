package com.example.tipee.utils

import android.view.View

fun View.setEnableView(isEnable: Boolean){
    this.isEnabled = isEnable
    if (isEnable) {
        this.alpha = 1.0f
    } else {
        this.alpha = 0.3f
    }
}