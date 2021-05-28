package com.example.tipee.screen.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentHomepageBinding
import com.example.tipee.screen.homepage.tab.TabItemFragment
import com.example.tipee.screen.main.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment: BaseFragment() {
    private lateinit var mBinding: FragmentHomepageBinding

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentHomepageBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun initData(){
        val pagerAdapter = PagerAdapter(requireActivity())
        for (i in 0..2) {
            pagerAdapter.addFragment(TabItemFragment.newInstance(i))
        }
        mBinding.vpTabItem.apply {
            adapter = pagerAdapter
        }
        TabLayoutMediator(mBinding.tabLayout, mBinding.vpTabItem
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Gợi ý hôm nay"
                }

                2 -> {
                    tab.text = "Có thể bạn quan tâm"
                }

                1 -> {
                    tab.text = "HOT"
                }
            }
        }.attach()
    }

    override fun configViews() {

    }
}