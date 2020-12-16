package com.example.tipee.screen.userprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentUserProfileBinding

class ProfileFragment: BaseFragment() {
    private lateinit var mBinding: FragmentUserProfileBinding
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun initData() {

    }
}