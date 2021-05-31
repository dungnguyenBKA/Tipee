package com.example.tipee.screen.search

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.ActivitySearchBinding
import com.example.tipee.model.ProductDetail
import com.example.tipee.screen.homepage.SearchViewModel
import com.example.tipee.screen.homepage.adapter.ProductAdapter
import com.example.tipee.screen.productdetail.ProductDetailActivity
import com.example.tipee.utils.hideKeyboard

class SearchActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, SearchActivity::class.java)
            context.startActivity(starter)
        }
    }
    private lateinit var mBinding: ActivitySearchBinding
    override fun getViewBinding(): View {
        mBinding = ActivitySearchBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        observableData()
    }

    private lateinit var viewModel : SearchViewModel
    private val mAdapter by lazy {
        ProductAdapter(object : ProductAdapter.OnViewClickListener{
            override fun onItemProductClick(productDetail: ProductDetail) {
                ProductDetailActivity.start(this@SearchActivity, productDetail.id, true)
            }
        })
    }
    override fun configViews() {
        mBinding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(mBinding.edtSearch.text.toString().trim())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        mBinding.ivNavigation.setOnClickListener {
            onBackPressed()
        }

        mBinding.rvSearch.run {
            adapter = mAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }

        mBinding.textField.setEndIconOnClickListener {
            mAdapter.submitList(mutableListOf())
            mBinding.edtSearch.setText("")
        }
    }

    private fun performSearch(keyword: String) {
        if (keyword.length < 2) {
            Toast.makeText(this,  "Tìm kiếm ít nhất 2 kí tự !" ,Toast.LENGTH_SHORT).show()
        } else {
            viewModel.getSearchData(keyword)
            hideKeyboard()
        }
    }

    override fun observableData() {
        viewModel.listItemSearch.observe(this, {
            mAdapter.submitList(it)
        })

        viewModel.isLoading.observe(this, {
            if(it) {
                showLoadingScreen(mBinding.rvSearch)
            } else {
                closeLoadingScreen()
            }
        })
    }
}
