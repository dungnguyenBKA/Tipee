package com.example.tipee.screen.test.banner

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
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
    var currentPage = 0
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
        pagerAdapter = BannerPagerAdapter(this@TestBannerActivity, arrayListOf("1","2","3","4"))
        val rAdapter = BannerPagerRecyclerAdapter(arrayListOf("1","2","3","4"))
        mBinding.viewPagerBanner.apply {
            adapter = rAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    ((mBinding.viewPagerBanner.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(position) as BannerPagerRecyclerAdapter.ViewHolder).loadAnimation()
                }
            })
        }
        mBinding.indicator.setViewPager(mBinding.viewPagerBanner)

        mBinding.bannerP2.apply {
            adapter = rAdapter
            addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener{
                override fun onViewAttachedToWindow(p0: View?) {

                }

                override fun onViewDetachedFromWindow(p0: View?) {

                }
            })
        }

//        val handler = Handler()
//        val update = Runnable {
//            if (currentPage == pagerAdapter.data.size) {
//                currentPage = 0
//            }
//
//            //The second parameter ensures smooth scrolling
//            mBinding.viewPagerBanner.setCurrentItem(currentPage++, true)
//        }
//
//        Timer().schedule(object : TimerTask() {
//            override fun run() {
//                handler.post(update)
//            }
//        }, 2000, 2000)
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