package com.example.tipee.screen.test.banner

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.tipee.R
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.BannerViewBinding
import com.example.tipee.utils.hide
import com.example.tipee.utils.show
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener
import kotlin.collections.ArrayList

class TestBannerActivity : BaseActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, TestBannerActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var mBinding: BannerViewBinding
    override fun getViewBinding(): View {
        mBinding = BannerViewBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun configViews() {
        val data = arrayListOf("1", "2", "3", "4")

        mBinding.bannerView.loadDataBanner(data)

//        mBinding.bannerView.apply {
//            adapter = youthAdapter
//            addOnPageChangeListener(object : OnPageChangeListener {
//                override fun onPageScrolled(
//                    position: Int,
//                    positionOffset: Float,
//                    positionOffsetPixels: Int
//                ) {
//
//                }
//
//                override fun onPageSelected(position: Int) {
//                    showAnimation(position)
//                }
//
//                override fun onPageScrollStateChanged(state: Int) {
//
//                }
//
//            })
//            setIndicator(CircleIndicator(this@TestBannerActivity))
//            currentItem = 0
//        }
    }
}