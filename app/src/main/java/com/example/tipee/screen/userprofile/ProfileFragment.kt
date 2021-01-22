package com.example.tipee.screen.userprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tipee.R
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentUserProfileBinding
import com.example.tipee.screen.cart.CartActivity
import com.example.tipee.screen.favourite.FavouriteActivity
import com.example.tipee.screen.login.LoginActivity
import com.example.tipee.utils.*
import com.example.tipee.utils.event.BuyEvent
import com.example.tipee.utils.event.LoginEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ProfileFragment : BaseFragment() {
    private lateinit var mBinding: FragmentUserProfileBinding
    private var isLogin = false
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun initData() {

    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onUpdate(buyEvent: BuyEvent){
        onRefreshing()
    }

    @Subscribe
    fun onLogin(loginEvent: LoginEvent){
        onRefreshing()
    }


    override fun onRefreshing() {
        isLogin = LoginUtils.isLogin()

        if (isLogin) {
            mBinding.tvUserName.text = "Xin chào ${LoginUtils.getUserRealName()}"
            mBinding.tvBalance.text = "Số dư: ${MoneyUtils.toVND(LoginUtils.getUserBalance())}"
            LoadImage.loadImage(LoginUtils.getAvatar(), mBinding.ivAvatar)
            mBinding.llUser.show()
            mBinding.llSignIn.hide()
            mBinding.btnLogout.show()
        } else {
            mBinding.llUser.hide()
            mBinding.llSignIn.show()
            mBinding.btnLogout.hide()
            mBinding.ivAvatar.setImageResource(R.drawable.avatar_placeholder)
        }
    }

    override fun configViews() {
        onRefreshing()
        mBinding.btnLogout.setOnClickListener {
            LoginUtils.logout()
            onRefreshing()
        }

        mBinding.llSignIn.setOnClickListener {
            LoginActivity.start(requireContext())
        }

        mBinding.llFavoriteProduct.setOnClickListener {
            if (!LoginUtils.isLogin()) {
                LoginActivity.start(requireContext())
            } else {
                FavouriteActivity.start(requireContext())
            }
        }

        mBinding.llOrderManager.setOnClickListener {
            CartActivity.start(requireContext())
        }
    }
}