package com.example.tipee.screen.cart

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.tipee.base.BaseActivity
import com.example.tipee.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {
    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, CartActivity::class.java)
            context.startActivity(starter)
        }
    }
    private lateinit var mBinding: ActivityCartBinding
    private lateinit var mAdapter: CartAdapter
    override fun getViewBinding(): View {
        mBinding = ActivityCartBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        mAdapter = CartAdapter(object : CartAdapter.OnCartItemClickListener{
            override fun onItemClick() {

            }

            override fun onItemDelete(position: Int) {
                mAdapter.notifyItemRemoved(position)
            }
        })
    }

    override fun configViews() {
        mBinding.ivBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.rvCart.apply {
            adapter = mAdapter
        }
    }
}