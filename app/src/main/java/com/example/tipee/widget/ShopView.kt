package com.example.tipee.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import com.example.tipee.databinding.ShopViewBinding
import com.example.tipee.utils.LoadImage

class ShopView : LinearLayout {
    interface OnShopViewClickListener{
        fun onFollowClick(shopId: String)
        fun onShopDetailClick(shopId: String)
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

    fun loadShopData(){
        //LoadImage.loadImage("", mBinding.ivShopLogo)
        mBinding.tvShopName.text = "Shopeeee"
        mBinding.rbShop.rating = 4.5f
        mBinding.tvTotalRating.text = "(Chưa có rating)"
        // TODO: 12/23/2020 add condition id
        mBinding.tvFollowShop.setOnClickListener {
            listener?.onFollowClick("123456")
        }
        mBinding.llShop.setOnClickListener {
            listener?.onShopDetailClick("123456")
        }
        mBinding.ivShopLogo.setOnClickListener {
            listener?.onShopDetailClick("123456")
        }
    }
}