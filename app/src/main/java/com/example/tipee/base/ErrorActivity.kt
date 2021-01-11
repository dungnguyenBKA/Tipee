package com.example.tipee.base

import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.tipee.databinding.ActivityErrorBinding
import com.example.tipee.utils.Utils

class ErrorActivity : BaseActivity() {
    companion object{
        const val ERROR_TYPE = "error_type"
        const val NO_INTERNET = "no_internet"
    }

    private lateinit var mBinding: ActivityErrorBinding
    override fun getViewBinding(): View {
        mBinding = ActivityErrorBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun initData() {
        intent.extras?.let { b ->
            b.getString(ERROR_TYPE)?.let {
                when(it){
                    NO_INTERNET -> {
                        mBinding.tvErrorDescription.text = "Kiểm tra kết nối mạng và thử lại"
                    }
                }
            }
        }
    }

    override fun configViews() {
        mBinding.btnRefresh.setOnClickListener {
            showLoadingScreen(mBinding.root)
            if (Utils.isNetworkConnected(applicationContext)){
                setResult(RESULT_OK)
                finish()
            } else {
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    closeLoadingScreen()
                }, 1000)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}