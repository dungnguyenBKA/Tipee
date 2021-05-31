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
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.room.Room
import com.example.tipee.R
import com.example.tipee.base.BaseActivity
import com.example.tipee.database.AppDatabase
import com.example.tipee.database.entity.Order
import com.example.tipee.databinding.ActivityProductDetailBinding
import com.example.tipee.model.Comment
import com.example.tipee.model.ProductDetail
import com.example.tipee.model.UserDetail
import com.example.tipee.screen.cart.CartActivity
import com.example.tipee.screen.main.PlaceHolderActivity
import com.example.tipee.screen.productdetail.adapter.ImageAdapter
import com.example.tipee.screen.productdetail.adapter.ShopAdapter
import com.example.tipee.screen.productdetail.adapter.ShopDetail
import com.example.tipee.screen.shopdetail.ShopDetailActivity
import com.example.tipee.utils.*
import com.example.tipee.utils.event.DeleteCartEvent
import com.example.tipee.widget.HtmlActivity
import com.example.tipee.widget.commentbox.CommentBox
import com.example.tipee.widget.popup.AddToCartBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

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
    private lateinit var imageAdapter: ImageAdapter
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
        viewModel.load(id)
        CoroutineScope(IO).launch {
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
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(mBinding.rvImageDetail)
        imageAdapter = ImageAdapter(object : ImageAdapter.OnViewClickListener {
            override fun onViewClick() {
                PlaceHolderActivity.start(this@ProductDetailActivity)
            }
        })

        mBinding.rvShop.adapter = mShopAdapter

        val listComment = arrayListOf<Comment>()
        listComment.add(
            Comment(
                UserDetail(
                    "",
                    "",
                    "",
                    "Nguyễn Minh Dũng",
                    0,
                    "https://i.pinimg.com/originals/b2/ce/be/b2cebefb7620f3f6ca0dc77ce92b8bbb.jpg"

                ),
                "Sản phẩm tốt, sẽ ủng hộ shop dài dài",
                5.toFloat()
            )
        )

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
            bindComment(listComment)
            listener = object : CommentBox.OnAddNewCommentListener{
                override fun onAddNewCommentListener() {
                    mBinding.edtComment.requestFocus()
                    imm.showSoftInput(mBinding.edtComment, InputMethodManager.SHOW_IMPLICIT)
                    mBinding.btnSend.setOnClickListener {
                        val comment = Comment(
                            LoginUtils.getUserDetail(),
                            mBinding.edtComment.text.toString(),
                            mBinding.ratingBar.rating
                        )
                        addComment(comment)
                        imm.hideSoftInputFromWindow(this@ProductDetailActivity.currentFocus?.windowToken, 0);
                    }
                }
            }
        }

        mBinding.rvImageDetail.apply {
            adapter = imageAdapter
        }
//        mBinding.shopView.setOnShopClickListener(object : ShopView.OnShopViewClickListener {
//            override fun onFollowClick(shopId: String) {
//                Toast.makeText(this@ProductDetailActivity, "Cảm ơn bạn đã theo dõi shop", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onShopDetailClick(shopId: String) {
//                ShopDetailActivity.start(this@ProductDetailActivity, "tiki-trading")
//            }
//        })

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
    }

    override fun onRefreshing() {
        viewModel.load(id)
    }

    private fun bindDetailProduct(productDetail: ProductDetail) {
        this.productDetail = productDetail
        toggleLikeBtn(productDetail.isLike)
        imageAdapter.listUrls = arrayListOf(productDetail.thumbnail_url)
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

        CoroutineScope(Main).launch {
            if (db.orderDao().findOrderByProductId(id).isNotEmpty()) {
                mBinding.tvPick.text = "Sửa đơn hàng"
            } else {
                mBinding.tvPick.text = "Chọn mua"
            }
        }

        mBinding.ivLike.setOnClickListener {
            try {
                if(!productDetail.isLike){
                    CoroutineScope(IO).launch {
                        db.productDao().insertProduct(productDetail)
                    }
                } else {
                    CoroutineScope(IO).launch {
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

            CoroutineScope(IO).launch {
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