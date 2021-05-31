package com.example.tipee.screen.productdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.tipee.R
import com.example.tipee.base.BaseActivity
import com.example.tipee.database.AppDatabase
import com.example.tipee.database.entity.Order
import com.example.tipee.databinding.ActivityProductDetailBinding
import com.example.tipee.model.ProductDetail
import com.example.tipee.model.Review
import com.example.tipee.screen.cart.CartActivity
import com.example.tipee.screen.productdetail.adapter.ImageAdapterBanner
import com.example.tipee.screen.productdetail.adapter.ShopAdapter
import com.example.tipee.screen.productdetail.adapter.ShopDetail
import com.example.tipee.screen.shopdetail.ShopDetailActivity
import com.example.tipee.utils.*
import com.example.tipee.utils.event.DeleteCartEvent
import com.example.tipee.widget.HtmlActivity
import com.example.tipee.widget.commentbox.CommentBox
import com.example.tipee.widget.popup.AddToCartBottomSheet
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

@SuppressLint("SetTextI18n")
class ProductDetailActivity : BaseActivity() {
    companion object {
        const val ID = "id"

        @JvmStatic
        fun start(context: Context, id: String, isTiki: Boolean = false) {
            val starter = Intent(context, ProductDetailActivity::class.java)
                .putExtra(ID, id)
                .putExtra("isTiki", isTiki)
            context.startActivity(starter)
        }
    }

    private lateinit var mBinding: ActivityProductDetailBinding
    private lateinit var viewModel: ProductDetailViewModel
    private var id: String = ""
    private var isFromTIki = true
    private lateinit var db: AppDatabase
    private lateinit var productDetail: ProductDetail
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

            it.getBoolean("isTiki").let{
                isFromTIki = it
            }
        }

        lifecycleScope.launch {
            try {
                val product = db.productDao().findProductById(id)
                Handler(Looper.getMainLooper()).post {
                    toggleLikeBtn(!product.isNullOrEmpty())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        observableData()
        onRefreshing()
    }

    private fun toggleLikeBtn(isLiked: Boolean) {
        if (isLiked) {
            mBinding.ivLike.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            mBinding.ivLike.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private val mShopAdapter by lazy {
        ShopAdapter(object: ShopAdapter.OnViewClickListener{
            override fun onItemClick(shop: ShopDetail) {
                ShopDetailActivity.start(this@ProductDetailActivity, shop)
            }
        })
    }
    override fun configViews() {
        mBinding.rvShop.adapter = mShopAdapter
        mBinding.rvShop.isNestedScrollingEnabled =false
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val softKeyboard = SoftKeyboard(mBinding.root, imm)
        softKeyboard.setSoftKeyboardCallback(object : SoftKeyboard.SoftKeyboardChanged{
            override fun onSoftKeyboardHide() {
                runOnUiThread{
                    mBinding.rlBuyBox.show()
                    mBinding.rlAddComment.hide()
                }
            }

            override fun onSoftKeyboardShow() {
                runOnUiThread{
                    mBinding.rlBuyBox.hide()
                    mBinding.rlAddComment.show()
                }
            }
        })

        mBinding.commentBox.apply {
            listener = object : CommentBox.OnAddNewCommentListener{
                override fun onAddNewCommentListener() {
                    mBinding.edtComment.requestFocus()
                    imm.showSoftInput(mBinding.edtComment, InputMethodManager.SHOW_IMPLICIT)
                    mBinding.btnSend.setOnClickListener {
                        val comment = Review(
                            "",
                            mBinding.edtComment.text.toString(),
                            mBinding.ratingBar.rating,
                            created_at = Date().time,
                            created_by = LoginUtils.getUserDetail()
                        )
                        addComment(comment)
                        hideKeyboard()
                    }
                }
            }
        }

        mBinding.ivCart.setOnClickListener {
            CartActivity.start(this)
        }

        mBinding.ivBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.ivShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                if(this@ProductDetailActivity::productDetail.isInitialized){
                    putExtra(Intent.EXTRA_TEXT, productDetail.name)
                } else {
                    putExtra(Intent.EXTRA_TEXT, "")
                }

                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Chia sẻ")
            startActivity(shareIntent)
        }
    }

    override fun observableData() {
        viewModel.mProductDetail.observe(this, { productDetail ->
            bindDetailProduct(productDetail)
        })

        viewModel.isLoading.observe(this, {
            if (it) {
                showLoadingScreen(mBinding.root)
            } else {
                closeLoadingScreen()
            }
        })

        viewModel.isError.observe(this, {
            if (it) {
                showError()
            }
        })

        viewModel.productReviews.observe(this, {
            mBinding.commentBox.bindComment(it)
        })
    }

    override fun onRefreshing() {
        viewModel.load(id)
        viewModel.loadReview(id)
    }

    private fun bindDetailProduct(productDetail: ProductDetail) {
        this.productDetail = productDetail
//        toggleLikeBtn(productDetail.isLike)
        mBinding.banner.run {
            addBannerLifecycleObserver(this@ProductDetailActivity)
            indicator = CircleIndicator(this@ProductDetailActivity)
            adapter = ImageAdapterBanner(productDetail.images.map { it.large_url })
        }
        LoadImage.loadImage(productDetail.thumbnail_url, mBinding.ivBlurProduct)
        mBinding.tvProductName.text = productDetail.name
        mBinding.tvShortDescription.text = productDetail.short_description
        mBinding.viewMore.setOnClickListener {
            HtmlActivity.start(this, "Thông tin & Giới thiệu", productDetail.description)
        }

        val listShop = mutableListOf<ShopDetail>()
        listShop.add(productDetail.current_seller)
        listShop.addAll(productDetail.other_sellers)
        mShopAdapter.submitList(listShop)

        lifecycleScope.launchWhenCreated {
            if (db.orderDao().findOrderByProductId(id).isNotEmpty()) {
                mBinding.tvPick.text = "Sửa đơn hàng"
            } else {
                mBinding.tvPick.text = "Chọn mua"
            }
        }

        mBinding.ivLike.setOnClickListener {
            try {
                if(!productDetail.isLike){
                    lifecycleScope.launch {
                        db.productDao().insertProduct(productDetail)
                    }
                } else {
                     lifecycleScope.launch {
                         db.productDao().delete(productDetail)
                     }
                }

                productDetail.isLike = !productDetail.isLike
                toggleLikeBtn(productDetail.isLike)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        mBinding.tvPick.setOnClickListener {

            lifecycleScope.launch {
                val orderFinder = db.orderDao().findOrderByProductId(productDetail.id)
                val order = if (orderFinder.isNotEmpty()) {
                    orderFinder[0]
                } else {
                    Order(
                        productId = productDetail.id,
                        productName = productDetail.name,
                        quantity = 1,
                        thumbnailUrl = productDetail.thumbnail_url,
                        price = productDetail.price,
                        listPrice = productDetail.list_price
                    )
                }

                AddToCartBottomSheet(
                    order,
                    object : AddToCartBottomSheet.OnAddCartListener {
                        override fun onAddCartSuccess(order: Order) {
                            runOnUiThread {
                                Toast.makeText(
                                    this@ProductDetailActivity,
                                    "Thành công",
                                    Toast.LENGTH_SHORT
                                ).show()
                                mBinding.tvPick.text = "Sửa đơn hàng"
                            }
                        }

                        override fun onAddFail() {
                            runOnUiThread {
                                Toast.makeText(
                                    this@ProductDetailActivity,
                                    "Có lỗi xảy ra",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }).show(supportFragmentManager, "")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onDeleteOrder(deleteCartEvent: DeleteCartEvent) {
        if (deleteCartEvent.order.productId == id) {
            mBinding.tvPick.text = "Chọn mua"
        }
    }
}