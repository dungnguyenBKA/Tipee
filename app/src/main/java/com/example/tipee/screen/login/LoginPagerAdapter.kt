package com.example.tipee.screen.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class LoginPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                SignInFragment.newInstance()
            }

            1 -> {
                SignUpFragment.newInstance()
            }

            else -> {
                Fragment()
            }
        }
    }
}