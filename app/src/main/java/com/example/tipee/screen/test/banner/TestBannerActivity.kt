package com.example.tipee.screen.test.banner

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.BannerViewBinding
import com.example.tipee.model.ProductDetail
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener

class TestBannerActivity : BaseActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, TestBannerActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onStart() {
        super.onStart()
        mBinding.banner.start()
    }

    override fun onStop() {
        super.onStop()
        mBinding.banner.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.banner.destroy()
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
        //setupBanner()
        pagerAdapter = BannerPagerAdapter(this@TestBannerActivity, arrayListOf("1","2","3"))
        mBinding.viewPagerBanner.apply {
            adapter = pagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    Log.d("zun", "$position")
                    if(pagerAdapter.getItem(position) is BlankFragment){
                        (pagerAdapter.getItem(position) as BlankFragment).loadAnimation()
                    }
                }
            })
        }
    }

    private lateinit var mAdapter: ImageAdapter

    private fun setupBanner() {
        Log.d("zun", "start ")
        val list = arrayListOf<ProductDetail>()
        list.add(ProductDetail("", "", "", "", ""))
        list.add(ProductDetail("", "", "", "", ""))
        list.add(ProductDetail("", "", "", "", ""))
        list.add(ProductDetail("", "", "", "", ""))

        mAdapter = ImageAdapter(list)

        mBinding.banner.addBannerLifecycleObserver(this).apply {
            adapter = mAdapter
            indicator = CircleIndicator(this@TestBannerActivity)
            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    Log.d("zun", "onpageScrool: $position")

                }

                override fun onPageSelected(position: Int) {
                    Log.d("zun", "onPageSelected: $position")
                    mAdapter.notifyItemChanged(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    Log.d("zun", "onPageScrollStateChanged: $state")

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