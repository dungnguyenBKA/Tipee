package com.example.tipee.screen.test.banner

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tipee.R
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentBannerBinding


class BlankFragment : BaseFragment() {

    private lateinit var mBinding: FragmentBannerBinding
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentBannerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    private var str = ""
    override fun initData() {
        arguments?.getString("url")?.let {
            str = it
        }
    }

    override fun configViews() {

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString("url", param1)
                }
            }
    }

    fun loadAnimation(){
        val animator = ObjectAnimator.ofFloat(mBinding.layer1, "translationX", 200f).apply {
            duration = 1000
        }
        animator.start()
    }
}