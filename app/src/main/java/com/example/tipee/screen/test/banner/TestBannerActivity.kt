package com.example.tipee.screen.test.banner

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.BannerViewBinding
import com.example.tipee.utils.makeStatusBarTransparent

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
        makeStatusBarTransparent()
    }

    override fun configViews() {
        val data = arrayListOf("1", "2", "", "")
        mBinding.bannerView.loadDataBanner(data)
    }
}