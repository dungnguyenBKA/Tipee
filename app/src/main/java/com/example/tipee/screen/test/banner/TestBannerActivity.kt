package com.example.tipee.screen.test.banner

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.BannerViewBinding
import com.example.tipee.model.ProductDetail
import com.youth.banner.indicator.CircleIndicator

class TestBannerActivity : BaseActivity() {
    companion object{
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

    }

    override fun configViews() {
        setupBanner()
    }

    private fun setupBanner(){
        val list = arrayListOf<ProductDetail>()
        list.add(ProductDetail("","","","",""))
        list.add(ProductDetail("","","","",""))
        list.add(ProductDetail("","","","",""))
        list.add(ProductDetail("","","","",""))
        mBinding.banner.addBannerLifecycleObserver(this).apply {
            adapter = ImageAdapter(list)
            indicator = CircleIndicator(this@TestBannerActivity)
        }
    }
}