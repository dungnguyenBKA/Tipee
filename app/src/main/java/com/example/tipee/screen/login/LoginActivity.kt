package com.example.tipee.screen.login

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayoutMediator

class LoginActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
    private lateinit var mBinding: ActivityLoginBinding
    override fun getViewBinding(): View {
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
    }

    override fun configViews() {
        mBinding.viewPager.apply {
            offscreenPageLimit = 2
            isUserInputEnabled = false
            adapter = LoginPagerAdapter(this@LoginActivity)
        }
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager
        ) { tab, position ->
            when(position){
                0 -> {
                    tab.text = "Đăng nhập"
                }

                1 -> {
                    tab.text = "Đăng kí"
                }
            }
        }.attach()
        mBinding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

}