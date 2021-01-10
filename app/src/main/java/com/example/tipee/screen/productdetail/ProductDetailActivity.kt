package com.example.tipee.screen.productdetail

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.room.Room
import com.example.tipee.base.BaseActivity
import com.example.tipee.database.AppDatabase
import com.example.tipee.database.entity.Order
import com.example.tipee.databinding.ActivityProductDetailBinding
import com.example.tipee.model.ProductDetail
import com.example.tipee.screen.cart.CartActivity
import com.example.tipee.screen.main.PlaceHolderActivity
import com.example.tipee.screen.productdetail.adapter.ImageAdapter
import com.example.tipee.utils.LoadImage
import com.example.tipee.widget.HtmlActivity
import com.example.tipee.widget.ShopView
import com.example.tipee.widget.popup.AddToCartBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductDetailActivity : BaseActivity() {
    companion object {
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
    private lateinit var db: AppDatabase
    override fun getViewBinding(): View {
        mBinding = ActivityProductDetailBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "Tipee"
        ).build()
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        intent.extras?.let { it ->
            it.getString(ID)?.let {
                id = it
            }
        }
        viewModel.loadProductDetail(id)
        observableData()
    }

    override fun configViews() {
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(mBinding.rvImageDetail)
        imageAdapter = ImageAdapter(object : ImageAdapter.OnViewClickListener {
            override fun onViewClick() {
                PlaceHolderActivity.start(this@ProductDetailActivity)
            }
        })

        mBinding.rvImageDetail.apply {
            adapter = imageAdapter
        }
        mBinding.shopView.setOnShopClickListener(object : ShopView.OnShopViewClickListener {
            override fun onFollowClick(shopId: String) {
                Toast.makeText(
                    this@ProductDetailActivity,
                    "follow shop clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onShopDetailClick(shopId: String) {
                Toast.makeText(
                    this@ProductDetailActivity,
                    "detail shop clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        mBinding.ivCart.setOnClickListener {
            CartActivity.start(this)
        }

        mBinding.ivBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.ivLike.setOnClickListener {

        }

        mBinding.ivShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Bruh")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Chia sẻ")
            startActivity(shareIntent)
        }
    }

    private fun observableData() {
        viewModel.mProductDetail.observe(this, Observer { productDetail ->
            bindDetailProduct(productDetail)

            mBinding.tvPick.setOnClickListener {
                AddToCartBottomSheet(
                    productDetail,
                    object : AddToCartBottomSheet.OnAddCartListener {
                        override fun onAddCart(order: Order) {
                            CoroutineScope(IO).launch {
                                try {
                                    db.orderDao().insertOrder(order)
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@ProductDetailActivity,
                                            "Thêm thành công",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@ProductDetailActivity,
                                            "Có lỗi xảy ra",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }).show(supportFragmentManager, "")
            }
        })

        viewModel.isLoading.observe(this, Observer {
            if (it) {
                // showLoadingScreen
            } else {
                // closeLoadingScreen
            }
        })

        viewModel.isError.observe(this, Observer {
            if (it) {
                showError()
            }
        })
    }

    override fun onRefreshing() {
        viewModel.loadProductDetail(id)
    }

    private fun bindDetailProduct(productDetail: ProductDetail) {
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