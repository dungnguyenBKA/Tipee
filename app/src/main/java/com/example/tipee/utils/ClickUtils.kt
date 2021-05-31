package com.example.tipee.utils

import android.os.SystemClock
import android.view.View


abstract class OnSingleV2ClickListener : View.OnClickListener {
    private var mLastClickTime: Long = 0
    abstract fun onSingleClick(v: View?)
    override fun onClick(v: View?) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime
        if (elapsedTime <= MIN_CLICK_INTERVAL) return
        onSingleClick(v)
    }

    companion object {
        private const val MIN_CLICK_INTERVAL: Long = 500
    }
}

fun View.setOnSingleClickListener(mOnClick: (v: View?) -> Unit) {
    setOnClickListener(object : OnSingleV2ClickListener() {
        override fun onSingleClick(v: View?) {
            mOnClick(v)
        }
    })
}