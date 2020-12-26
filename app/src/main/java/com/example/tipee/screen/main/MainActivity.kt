package com.example.tipee.screen.main

import android.view.View
import com.example.tipee.R
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.ActivityMainBinding
import com.example.tipee.screen.homepage.HomePageFragment
import com.example.tipee.screen.userprofile.ProfileFragment

class MainActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun getViewBinding(): View {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        val pagerAdapter = PagerAdapter(this)
        pagerAdapter.addFragment(HomePageFragment())
        pagerAdapter.addFragment(ProfileFragment())
        mBinding.viewPager.apply {
            offscreenPageLimit = 2
            isUserInputEnabled = false
            adapter = pagerAdapter
        }

        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_homepage -> {
                    mBinding.viewPager.currentItem = 0
                }

                R.id.main_profile -> {
                    mBinding.viewPager.currentItem = 1
                }
                else -> mBinding.viewPager.currentItem = 0
            }
            true
        }
    }

    override fun configViews() {

    }
}