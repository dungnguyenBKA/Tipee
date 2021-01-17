package com.example.tipee.screen.favourite

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.room.Room
import com.example.tipee.base.BaseActivity
import com.example.tipee.database.AppDatabase
import com.example.tipee.databinding.ActivityFavouriteBinding
import com.example.tipee.model.ProductDetail
import com.example.tipee.screen.productdetail.ProductDetailActivity
import com.scwang.smart.refresh.header.MaterialHeader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FavouriteActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, FavouriteActivity::class.java)
            context.startActivity(starter)
        }
    }
    private lateinit var mBinding: ActivityFavouriteBinding
    private lateinit var db: AppDatabase
    private lateinit var mAdapter: Category2Adapter
    private var listProduct = arrayListOf<ProductDetail>()
    override fun getViewBinding(): View {
        mBinding = ActivityFavouriteBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "Tipee"
        ).build()

        mAdapter = Category2Adapter(listProduct, object : Category2Adapter.OnViewClickListener{
            override fun onItemProductClick(productDetail: ProductDetail) {
                ProductDetailActivity.start(this@FavouriteActivity, productDetail.id)
            }

            override fun onItemDelete(productDetail: ProductDetail, adapterPosition: Int) {
                if(listProduct.contains(productDetail)){
                    try {
                        CoroutineScope(IO).launch {
                            db.productDao().delete(productDetail)
                        }
                        listProduct.remove(productDetail)
                        mAdapter.notifyItemRemoved(adapterPosition)
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        })

        CoroutineScope(IO).launch {
            listProduct.clear()
            listProduct.addAll(db.productDao().getAll())

            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onRefreshing() {
        CoroutineScope(IO).launch {
            listProduct.clear()
            listProduct.addAll(db.productDao().getAll())
            runOnUiThread{
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun configViews() {
        mBinding.rvFavour.apply {
            adapter = mAdapter
        }

        mBinding.refreshLayout.apply {
            setRefreshHeader(MaterialHeader(this@FavouriteActivity))
            setOnRefreshListener {
                onRefreshing()
                it.finishRefresh(2000)
            }
        }

        mBinding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}