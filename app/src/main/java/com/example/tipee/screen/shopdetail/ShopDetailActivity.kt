package com.example.tipee.screen.shopdetail

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.ActivityShopDetailBinding
import com.example.tipee.model.ProductDetail
import com.example.tipee.screen.productdetail.ProductDetailActivity
import com.example.tipee.utils.hideKeyboard
import com.scwang.smart.refresh.header.MaterialHeader

class ShopDetailActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ShopDetailActivity::class.java)
            context.startActivity(starter)
        }
    }
    private lateinit var mBinding: ActivityShopDetailBinding
    override fun getViewBinding(): View {
        mBinding = ActivityShopDetailBinding.inflate(layoutInflater)
        return mBinding.root
    }
    private lateinit var mAdapter : ShopItemAdapter
    private var mListProduct = arrayListOf<ProductDetail>()
    private var mListFilter = mutableListOf<ProductDetail>()
    private lateinit var viewModel: ShopDetailViewModel
    override fun initData() {
        viewModel = ViewModelProvider(this).get(ShopDetailViewModel::class.java)
        viewModel.loadShopDetail("tiki-trading")
        observableData()
    }

    override fun observableData() {
        viewModel.mListProduct.observe(this, Observer {
            mListProduct.clear()
            mListProduct.addAll(it)
            mAdapter.updateData(it)
        })

        viewModel.isLoading.observe(this, {
            if(it){
                showLoadingScreen(mBinding.root)
            } else{
                closeLoadingScreen()
            }
        })
    }

    override fun onRefreshing() {
        viewModel.loadShopDetail("tiki-trading")
    }

    override fun configViews() {
        mBinding.refreshLayout.setRefreshHeader(MaterialHeader(this))
        mBinding.refreshLayout.setOnRefreshListener {
            onRefreshing()
            it.finishRefresh(2000)
        }

        mAdapter = ShopItemAdapter(object : ShopItemAdapter.OnViewClickListener{
            override fun onViewClick(productDetail: ProductDetail) {
                ProductDetailActivity.start(this@ShopDetailActivity, productDetail.id)
            }
        })

        mBinding.rvShop.run {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = mAdapter
        }

        mBinding.tvSearch.editText?.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    v?.let {
                        searchInListProduct(v.text.toString().trim())
                    }
                    return true
                }
                return false
            }
        })
    }

    fun searchInListProduct(text: String){
        hideKeyboard()
        mListFilter.clear()
        if(text.isEmpty()){
            mAdapter.updateData(mListProduct)
            return
        }
        mListProduct.forEach { item ->
            if(item.name.contains(text, true)){
                mListFilter.add(item)
            }
        }
        mAdapter.updateData(mListFilter)
    }
}