package com.example.tipee.widget.banner

import android.animation.ObjectAnimator
import android.view.View
import com.example.tipee.utils.hide
import com.example.tipee.utils.show

data class BannerItemHolder (
        var anim: ObjectAnimator,
        var view: View
){
    fun loadAnim(){
        view.show()
        anim.start()
    }

    fun hideView(){
        view.hide()
        anim.cancel()
    }
}