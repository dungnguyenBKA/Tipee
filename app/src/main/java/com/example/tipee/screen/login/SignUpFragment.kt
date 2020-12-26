package com.example.tipee.screen.login

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentSignUpBinding
import com.example.tipee.utils.setEnableView
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment : BaseFragment() {
    private lateinit var mBinding: FragmentSignUpBinding

    private var username: String = ""
    private var password: String = ""

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun initData() {
        arguments?.let {

        }
    }

    override fun configViews() {
        mBinding.password.editText?.doAfterTextChanged {
            it?.let { validateText(it, mBinding.password) }
        }

        mBinding.username.editText?.doAfterTextChanged {
            it?.let { validateText(it, mBinding.username) }
        }

        mBinding.name.editText?.doAfterTextChanged {
            val text = it.toString().trim()
            if (text.isEmpty()){
                mBinding.btnSubmit.setEnableView(false)
                mBinding.name.error = "Không được để trống"
            } else {
                mBinding.btnSubmit.setEnableView(true)
                mBinding.name.error = null
            }
        }

        mBinding.rePassword.editText?.doAfterTextChanged {
            if(mBinding.password.editText?.text.toString() != mBinding.rePassword.editText?.text.toString()){
                mBinding.btnSubmit.setEnableView(false)
                mBinding.rePassword.error = "Chưa giống password bên trên"
            } else {
                mBinding.btnSubmit.setEnableView(true)
                mBinding.rePassword.error = null
            }
        }

        mBinding.btnSubmit.setEnableView(false)
        mBinding.btnSubmit.setOnClickListener {
            if (!it.isEnabled) return@setOnClickListener
        }
    }

    private fun validateText(it: Editable, textInputLayout: TextInputLayout) {
        when {
            it.toString().trim().isEmpty() -> {
                mBinding.btnSubmit.setEnableView(false)
                textInputLayout.error = "Không được để trống"
            }
            it.toString().trim().length <= 8 -> {
                mBinding.btnSubmit.setEnableView(false)
                textInputLayout.error = "Số kí tự phải lớn hơn 8"
            }
            it.toString().trim().length > 20 -> {
                mBinding.btnSubmit.setEnableView(false)
                textInputLayout.error = "Số kí tự không được quá 20"
            }
            else -> {
                mBinding.btnSubmit.setEnableView(true)
                textInputLayout.error = null
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}