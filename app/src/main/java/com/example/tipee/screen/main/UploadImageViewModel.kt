package com.example.tipee.screen.main

import androidx.lifecycle.MutableLiveData
import com.example.tipee.base.BaseViewModel
import com.example.tipee.network.RetrofitHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody

class UploadImageViewModel: BaseViewModel() {
    private val api = RetrofitHelper.getImgurInstance()
    data class ImgurRes(
        var link: String
    )

    var imgurRes = MutableLiveData<ImgurRes>()

    fun uploadImage(stringBase64: String){
        isLoading.value = true
        val base64 = MultipartBody.Part.createFormData(
            "image",
            stringBase64
        )
        api.uploadImage(base64)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.value = false
                imgurRes.value = it.data
            }, onError)
    }
}

