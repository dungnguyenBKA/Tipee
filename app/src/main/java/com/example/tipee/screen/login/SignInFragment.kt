package com.example.tipee.screen.login

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentSignInBinding
import com.example.tipee.utils.setEnableView
import com.google.android.material.textfield.TextInputLayout


class SignInFragment : BaseFragment() {
    private lateinit var mBinding: FragmentSignInBinding

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentSignInBinding.inflate(inflater, container, false)
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

        mBinding.btnSubmit.setEnableView(false)
        mBinding.btnSubmit.setOnClickListener {
            if(!it.isEnabled) return@setOnClickListener
            Toast.makeText(requireContext(), "username: ${mBinding.username.editText?.text} pass: ${mBinding.password.editText?.text}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateText(it: Editable, textInputLayout: TextInputLayout){
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
        fun newInstance() = SignInFragment()
    }
}