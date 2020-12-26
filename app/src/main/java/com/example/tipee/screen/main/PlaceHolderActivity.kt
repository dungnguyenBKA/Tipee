package com.example.tipee.screen.main

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.FragmentPlaceHolderBinding

class PlaceHolderActivity : BaseActivity() {
    private lateinit var mBinding: FragmentPlaceHolderBinding
    override fun getViewBinding(): View {
        mBinding = FragmentPlaceHolderBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {

    }

    override fun configViews() {

    }

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PlaceHolderActivity::class.java)
            context.startActivity(starter)
        }
    }
}