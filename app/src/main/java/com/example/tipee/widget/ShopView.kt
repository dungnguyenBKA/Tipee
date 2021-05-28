package com.example.tipee.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.tipee.databinding.ShopViewBinding
import com.example.tipee.screen.productdetail.adapter.ShopDetail
import com.example.tipee.utils.LoadImage

class ShopView : LinearLayout {
    interface OnShopViewClickListener{
        fun onFollowClick(shop: ShopDetail)
        fun onShopDetailClick(shop: ShopDetail)
    }
    private var listener: OnShopViewClickListener? = null
    private lateinit var mBinding: ShopViewBinding

    constructor(context: Context): super(context){
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    private fun initView(context: Context?, attrs: AttributeSet?){
        mBinding = ShopViewBinding.inflate(LayoutInflater.from(context), this, true)

        attrs?.let {

        }
    }

    private fun initView(context: Context?){
        initView(context, null)
    }

    fun setOnShopClickListener(onShopViewClickListener: OnShopViewClickListener){
        this.listener = onShopViewClickListener
    }

    fun loadShopData(shop: ShopDetail){
        LoadImage.loadImage(shop.logo, mBinding.ivShopLogo)
        mBinding.tvShopName.text = shop.name
        mBinding.rbShop.rating = 4f
        mBinding.tvTotalRating.text = "(Chưa có rating)"
        mBinding.tvFollowShop.setOnClickListener {
            listener?.onFollowClick(shop)
        }
        mBinding.llShop.setOnClickListener {
            listener?.onShopDetailClick(shop)
        }
        mBinding.ivShopLogo.setOnClickListener {
            listener?.onShopDetailClick(shop)
        }
    }
}