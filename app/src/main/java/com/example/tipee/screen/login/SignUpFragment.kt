package com.example.tipee.screen.login

import android.content.Intent
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.tipee.base.BaseFragment
import com.example.tipee.databinding.FragmentSignUpBinding
import com.example.tipee.model.UserDetail
import com.example.tipee.screen.main.UploadImageViewModel
import com.example.tipee.utils.LoadImage
import com.example.tipee.utils.LoginUtils
import com.example.tipee.utils.convertImageToBase64
import com.example.tipee.utils.setEnableView
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class SignUpFragment : BaseFragment() {
    private lateinit var mBinding: FragmentSignUpBinding
    private var urlAvatar = ""

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    private lateinit var mViewModel: LoginViewModel
    private lateinit var uploadImageViewModel: UploadImageViewModel
    override fun initData() {
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        uploadImageViewModel = ViewModelProvider(this).get(UploadImageViewModel::class.java)
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
            val userDetail = UserDetail(
                UUID.randomUUID().toString().substring(0, 8),
                mBinding.username.editText!!.text.toString(),
                mBinding.password.editText!!.text.toString(),
                mBinding.name.editText!!.text.toString(),
                1000000,
                urlAvatar
            )

            LoginUtils.login(userDetail)
            requireActivity().finish()
            //mViewModel.uploadUser(userDetail)
        }

        mBinding.btnPickAvatar.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 229)
        }

        observableData()
    }

    override fun observableData() {
        mViewModel.isLoading.observe(this, {
            if(it){
                showLoadingScreen(mBinding.root)
            } else {
                closeLoadingScreen()
            }
        })

        uploadImageViewModel.isLoading.observe(this, {
            if(it){
                showLoadingScreen(mBinding.root)
            } else {
                closeLoadingScreen()
            }
        })

        uploadImageViewModel.isError.observe(this, {
            Toast.makeText(requireContext(), "Có lỗi trong quá trình xử lý", Toast.LENGTH_SHORT).show()
        })

        uploadImageViewModel.imgurRes.observe(this, {
            LoadImage.loadImage(it.link, mBinding.ivAvatar)
            urlAvatar = it.link
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode != 229 || data == null){
            return
        }
        if (resultCode == AppCompatActivity.RESULT_OK) {
            try {
                data.data?.let {uri ->
                    val base64Image = requireContext().convertImageToBase64(uri)
                    uploadImageViewModel.uploadImage(base64Image)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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