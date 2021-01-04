package com.example.tipee.widget.banner

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.tipee.R
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

    var totalAnimationLayout = arrayListOf<List<BannerItemHolder>>()

    fun loadDataBanner(data: List<String>) {
        totalAnimationLayout.clear()
        // load animation to list
        data.forEach { coverDetail ->
            val itemHolder = mutableListOf<BannerItemHolder>()
//            if (coverDetail.bannerType == "animation") {
//                coverDetail.animations?.let {
//                    it.forEach { imageAnim ->
//                        addLayout(itemHolder, imageAnim.image, imageAnim.direction, imageAnim.distance, imageAnim.speed)
//                    }
//                }
//            }
            addLayout(itemHolder, R.drawable.b2, "left", 100f, 1500)
            addLayout(itemHolder, R.drawable.b3, "right", 100f, 1500)
            totalAnimationLayout.add(itemHolder)
        }

        mBinding.banner.apply {
            adapter = ImageAdapter(data)
            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageSelected(position: Int) {
                    showAnimation(position)
                }
            })
            setOnBannerListener { data, pos ->
                listener?.onBannerClick()
            }
            indicator = CircleIndicator(context)
            setIndicatorGravity(IndicatorConfig.Direction.LEFT)
        }
    }

    private fun showAnimation(position: Int) {
        if (position >= totalAnimationLayout.size) {
            return
        }

        totalAnimationLayout.forEachIndexed { index, list ->
            if (index == position) {
                list.forEach { it.loadAnim() }
            } else {
                list.forEach { it.hideView() }
            }
        }
    }

    private fun addLayout(
        itemHolder: MutableList<BannerItemHolder>,
        url: Int,
        direction: String,
        distance: Float,
        durationVal: Long
    ) {
        val newLayer = ImageView(context).apply {
            adjustViewBounds = true
            elevation = itemHolder.size.toFloat()
            //loadImage(url, this)
            setImageResource(url)
        }

        val moveDistance: Float
        val marginStartVal: Int
        val marginEndVal: Int

        when (direction) {
            "left" -> {
                marginStartVal = -distance.toInt()
                marginEndVal = distance.toInt()
                moveDistance = distance
            }

            "right" -> {
                marginStartVal = distance.toInt()
                marginEndVal = -distance.toInt()
                moveDistance = -distance
            }

            else -> {
                marginStartVal = 0
                marginEndVal = 0
                moveDistance = 0f
            }
        }

        val param = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        ).apply {
            marginStart = marginStartVal
            marginEnd = marginEndVal
        }
        mBinding.root.addView(newLayer, param)
        val animator = ObjectAnimator.ofFloat(newLayer, "translationX", moveDistance).apply {
            duration = durationVal
        }
        itemHolder.add(BannerItemHolder(animator, newLayer))
    }


//    private fun loadImage(url: String, imageView: ImageView) {
//        val requestOptions = RequestOptions()
//        requestOptions.centerCrop()
//        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
//        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
//        val thumbnail: RequestBuilder<Bitmap> = Glide.with(context).asBitmap()
//                .load(R.drawable.mb_thumbnail_loading_vertical).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
//        if (ImageUtils.isValidContextForGlide(context)) {
//            if (url.contains("http")) {
//                Glide.with(context)
//                        .asBitmap()
//                        .transition(BitmapTransitionOptions.withCrossFade(factory))
//                        .apply(requestOptions)
//                        .load(url)
//                        .thumbnail(thumbnail)
//                        .into(imageView)
//            } else {
//                Glide.with(context)
//                        .asBitmap()
//                        .transition(BitmapTransitionOptions.withCrossFade(factory))
//                        .apply(requestOptions)
//                        .load(DataConstants.domainUploading + url)
//                        .thumbnail(thumbnail)
//                        .into(imageView)
//            }
//        }
//    }
}