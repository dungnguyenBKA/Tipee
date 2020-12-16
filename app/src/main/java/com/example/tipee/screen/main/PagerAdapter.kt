package com.example.tipee.screen.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fa: FragmentActivity) :  FragmentStateAdapter(fa) {
    private var fragments = arrayListOf<Fragment>()

    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment{
        return fragments[position]
    }
}