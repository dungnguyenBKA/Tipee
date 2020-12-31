package com.example.tipee.screen.test.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.youth.banner.adapter.BannerAdapter

class BannerPagerAdapter(fa: FragmentActivity, var data : List<String>):  FragmentStateAdapter(fa){
    var fragments = arrayListOf<Fragment>()
    override fun getItemCount() = data.size

    override fun createFragment(position: Int): Fragment {
        fragments.add(BlankFragment.newInstance(data[position]))
        return fragments[position]
    }

    fun reverseAnimation(position: Int){
        if(position == -1) return
        if(fragments[position] is BlankFragment){
            (fragments[position] as BlankFragment).reverseAnimation()
        }
    }

    fun loadAnimation(position: Int){
        if(position >= fragments.size){
            return
        }
        if(fragments[position] is BlankFragment){
            (fragments[position] as BlankFragment).loadAnimation()
        }
    }
}