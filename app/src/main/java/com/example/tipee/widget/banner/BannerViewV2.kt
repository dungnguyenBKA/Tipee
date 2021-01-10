package com.example.tipee.widget.banner

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.RelativeLayout
import com.example.tipee.databinding.CustomViewBannerV2LayoutBinding
import com.example.tipee.screen.test.banner.ImageAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener

class BannerViewV2 : RelativeLayout {
    interface OnBannerClickListener {
        fun onBannerClick()
    }

    var listener: OnBannerClickListener? = null

    private lateinit var mBinding: CustomViewBannerV2LayoutBinding

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    private fun initView(context: Context?, attrs: AttributeSet?) {
        mBinding = CustomViewBannerV2LayoutBinding.inflate(LayoutInflater.from(context), this, true)
        start()
        attrs?.let {

        }
    }

    private fun initView(context: Context?) {
        initView(context, null)
    }

    fun start() {
        mBinding.banner.start()
    }

    fun stop() {
        mBinding.banner.stop()
    }

    private fun getScreenWidth(context: Context): Int {
        val manager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        return display.width
    }


    fun loadDataBanner(data: List<String>) {
        val mAdapter = ImageAdapter(data)
        val h = getScreenWidth(context) * 569 / 768
        mBinding.root.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, h)

        mBinding.banner.apply {
            adapter = mAdapter
            setLoopTime(3000)
            isAutoLoop(true)
            indicator = CircleIndicator(context)
            setIndicatorGravity(IndicatorConfig.Direction.LEFT)
            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {}

                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageSelected(position: Int) {
                    mAdapter.notifyItemChanged(position, "start")
                }
            })

            setOnBannerListener { data, pos ->
                listener?.onBannerClick()
            }
        }
        mBinding.banner.start()
        mBinding.banner.setCurrentItem(0, false)
    }
}