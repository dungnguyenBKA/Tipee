package com.example.tipee.screen.homepage.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tipee.R
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentTabItemBinding
import com.example.tipee.model.ProductDetail
import com.example.tipee.screen.homepage.HomepageViewModelV2
import com.example.tipee.screen.homepage.adapter.ProductAdapter
import com.example.tipee.screen.productdetail.ProductDetailActivity
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

private const val ARG_TAB_NUM = "param1"

class TabItemFragment : BaseFragment() {
    private var tab: Int = 0

    private lateinit var mViewModel: HomepageViewModelV2
    private lateinit var mBinding: FragmentTabItemBinding
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentTabItemBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun initData() {
        mViewModel = ViewModelProvider(this).get(HomepageViewModelV2::class.java)
        arguments?.let {
            tab = it.getInt(ARG_TAB_NUM)
        }

        mAdapter = ProductAdapter(object: ProductAdapter.OnViewClickListener {
            override fun onItemProductClick(productDetail: ProductDetail) {
                ProductDetailActivity.start(requireContext(), productDetail.id)
            }
        })

        observableData()
    }

    var listProducts = mutableListOf<ProductDetail>()
    private lateinit var mAdapter: ProductAdapter
    override fun configViews() {
        mBinding.rvTab.run {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = mAdapter
        }

        mBinding.refreshLayout.run {
            setRefreshHeader(MaterialHeader(requireContext()))
            setRefreshFooter(BallPulseFooter(requireContext()).apply {
                setNormalColor(ContextCompat.getColor(requireContext(), R.color.color_1FCF84))
            })

            setOnRefreshListener {
                onRefreshing()
                finishRefresh(1000)
            }

            setOnLoadMoreListener {
                loadMore()
                finishLoadMore(1000)
            }
        }
        onRefreshing()
    }

    override fun observableData() {
        mViewModel.tabItem.observe(this, {
            if(itemCount == 0) {
                itemCount = it.items.size
                listProducts.clear()
                listProducts.addAll(it.items)
                mAdapter.submitList(it.items)
            } else {
                listProducts.addAll(it.items)
                itemCount += listProducts.size
                mAdapter.submitList(listProducts)
            }
        })

        mViewModel.isLoading.observe(this, {
            if(it) {
                showLoadingScreen(mBinding.rvTab)
            } else {
                closeLoadingScreen()
            }
        })
    }

    companion object {
        fun newInstance(tabNum : Int) =
            TabItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TAB_NUM, tabNum)
                }
            }
    }

    var itemCount = 0
    override fun onRefreshing() {
        itemCount = 0
        mViewModel.getTabItems(tab, 0)
    }

    private fun loadMore() {
        mViewModel.getTabItems(tab, itemCount+1, false)
    }
}