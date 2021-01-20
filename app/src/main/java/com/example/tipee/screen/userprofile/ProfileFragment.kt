package com.example.tipee.screen.userprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentUserProfileBinding
import com.example.tipee.screen.favourite.FavouriteActivity
import com.example.tipee.screen.login.LoginActivity
import com.example.tipee.utils.LoginUtils

class ProfileFragment: BaseFragment() {
    private lateinit var mBinding: FragmentUserProfileBinding
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun initData() {

    }

    override fun configViews() {
        mBinding.rlUserInform.setOnClickListener {
            if(!LoginUtils.isLoginWithRequest()){
                LoginActivity.start(requireContext())
            }
        }

        mBinding.llFavoriteProduct.setOnClickListener {
//            if(!LoginUtils.isLoginWithRequest()){
//                LoginActivity.start(requireContext())
//            } else {
//                FavouriteActivity.start(requireContext())
//            }

            FavouriteActivity.start(requireContext())
        }
    }
}