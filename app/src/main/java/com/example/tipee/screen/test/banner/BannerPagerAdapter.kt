package com.example.tipee.screen.test.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerPagerAdapter(fa: FragmentActivity, var data : List<String>):  FragmentStateAdapter(fa){
    private var fragments = arrayListOf<Fragment>()
    override fun getItemCount() = data.size

    override fun createFragment(position: Int): Fragment {
        fragments.add(BlankFragment.newInstance(data[position]))
        return fragments[position]
    }

    fun getItem(position: Int): Fragment{
        return fragments[position]
    }
}