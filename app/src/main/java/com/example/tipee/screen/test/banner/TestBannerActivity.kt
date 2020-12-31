package com.example.tipee.screen.test.banner

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.BannerViewBinding
import com.example.tipee.model.ProductDetail
import com.example.tipee.screen.homepage.adapter.Category1Adapter
import com.example.tipee.screen.login.LoginPagerAdapter
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener
import java.util.*

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
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }

    private lateinit var pagerAdapter : BannerPagerAdapter

    override fun configViews() {
        pagerAdapter = BannerPagerAdapter(this@TestBannerActivity, arrayListOf("1","2","3"))
        mBinding.viewPagerBanner.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    Log.d("zun", "pos: $position")
                    pagerAdapter.loadAnimation(position)
                }
            })
            adapter = pagerAdapter
        }

        val bannerAdapter = ImageAdapter(arrayListOf("1","2","3"))
        mBinding.banner2.apply {
            adapter = bannerAdapter
            addOnPageChangeListener(object : OnPageChangeListener{
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                }

                override fun onPageScrollStateChanged(state: Int) {

                }

            })
        }

    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}