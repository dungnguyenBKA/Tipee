package com.example.tipee.screen.productdetail

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.ActivityProductDetailBinding
import com.example.tipee.model.ProductDetail
import com.example.tipee.screen.main.PlaceHolderActivity
import com.example.tipee.screen.productdetail.adapter.ImageAdapter
import com.example.tipee.utils.LoadImage
import com.example.tipee.widget.HtmlActivity
import com.example.tipee.widget.ShopView

class ProductDetailActivity : BaseActivity() {
    companion object{
        const val ID = "id"
        @JvmStatic
        fun start(context: Context, id: String) {
            val starter = Intent(context, ProductDetailActivity::class.java)
                .putExtra(ID, id)
            context.startActivity(starter)
        }
    }
    private lateinit var mBinding: ActivityProductDetailBinding
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var imageAdapter: ImageAdapter
    private var id: String = ""
    override fun getViewBinding(): View {
        mBinding = ActivityProductDetailBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        intent.extras?.let { it ->
            it.getString(ID)?.let {
                id = it
            }
        }
        observableData()
    }

    override fun configViews() {
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(mBinding.rvImageDetail)
        imageAdapter = ImageAdapter(object : ImageAdapter.OnViewClickListener{
            override fun onViewClick() {
                PlaceHolderActivity.start(this@ProductDetailActivity)
            }
        })

        mBinding.rvImageDetail.apply {
            adapter = imageAdapter
        }
        mBinding.shopView.setOnShopClickListener(object : ShopView.OnShopViewClickListener {
            override fun onFollowClick(shopId: String) {
                Toast.makeText(this@ProductDetailActivity, "follow shop clicked", Toast.LENGTH_SHORT).show()
            }

            override fun onShopDetailClick(shopId: String) {
                Toast.makeText(this@ProductDetailActivity, "detail shop clicked", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loadProductDetail(id)
    }

    private fun observableData(){
        viewModel.mProductDetail.observe(this, Observer { productDetail ->
            bindDetailProduct(productDetail)
        })

        viewModel.isLoading.observe(this, Observer {
            if(it){
                // showLoadingScreen
            } else {
                // closeLoadingScreen
            }
        })

        viewModel.isError.observe(this, Observer {
            if(it){
                showError()
            }
        })
    }

    override fun onRefreshing() {
        viewModel.loadProductDetail(id)
    }

    private fun bindDetailProduct(productDetail: ProductDetail){
        imageAdapter.listUrls = arrayListOf(productDetail.thumbnail_url)
        LoadImage.loadImage(productDetail.thumbnail_url, mBinding.ivBlurProduct)
        mBinding.tvProductName.text = productDetail.name
        mBinding.tvShortDescription.text = productDetail.short_description
        mBinding.shopView.loadShopData()
        mBinding.viewMore.setOnClickListener {
            HtmlActivity.start(this, "Thông tin & Giới thiệu", productDetail.description)
        }
    }
}