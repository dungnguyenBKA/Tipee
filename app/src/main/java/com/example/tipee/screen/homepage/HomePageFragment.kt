package com.example.tipee.screen.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentHomepageBinding
import com.example.tipee.model.response.HomepageItem
import com.example.tipee.screen.homepage.adapter.HomepageAdapter
import com.example.tipee.screen.main.PlaceHolderActivity
import com.scwang.smart.refresh.header.MaterialHeader

class HomePageFragment: BaseFragment() {
    private lateinit var mBinding: FragmentHomepageBinding
    private lateinit var mViewModel: HomepageViewModel
    private var homepageData = arrayListOf<HomepageItem>()
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentHomepageBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun initData(){
        mViewModel = ViewModelProvider(this).get(HomepageViewModel::class.java)
        observableData()
    }

    override fun onRefreshing() {
        mViewModel.loadHomepage()
    }

    override fun observableData() {
        mViewModel.homepageData.observe(this, {
            homepageData.clear()
            homepageData.addAll(it)
            if(this::mAdapter.isInitialized){
                mAdapter.notifyDataSetChanged()
            }
        })

        mViewModel.isLoading.observe(this, {
            if(it){
                showLoadingScreen(mBinding.root)
            } else {
                closeLoadingScreen()
            }
        })
    }

    private lateinit var mAdapter: HomepageAdapter
    override fun configViews() {
        mBinding.contentHomepage.refreshLayout.apply {
            setRefreshHeader(MaterialHeader(requireContext()))
            setOnRefreshListener {
                onRefreshing()
                it.finishRefresh(2000)
            }
        }
        mAdapter = HomepageAdapter(homepageData, object : HomepageAdapter.OnViewClickListener{
            override fun onTitleClick(idCategory: String) {
                PlaceHolderActivity.start(requireContext())
            }
        })
        mBinding.contentHomepage.rvHomePage.adapter = mAdapter
        mViewModel.loadHomepage()
    }
}