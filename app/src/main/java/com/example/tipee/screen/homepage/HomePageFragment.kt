package com.example.tipee.screen.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentHomepageBinding
import com.example.tipee.screen.homepage.adapter.Category1Adapter
import com.example.tipee.screen.homepage.adapter.HomepageAdapter
import com.example.tipee.screen.main.PlaceHolderActivity

class HomePageFragment: BaseFragment() {
    private lateinit var mBinding: FragmentHomepageBinding
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentHomepageBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun initData(){
        mBinding.contentHomepage.rvHomePage.adapter = HomepageAdapter(object : HomepageAdapter.OnViewClickListener{
            override fun onTitleClick() {
                PlaceHolderActivity.start(requireContext())
            }
        })
    }
}