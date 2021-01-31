package com.example.tipee.screen.test.banner

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.TestActivityBinding

class TestActivity : BaseActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, TestActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var mBinding: TestActivityBinding
    override fun getViewBinding(): View {
        mBinding = TestActivityBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
    }

    override fun configViews() {
    }
}