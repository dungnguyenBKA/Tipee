package com.example.tipee.screen.productdetail

import androidx.lifecycle.MutableLiveData
import com.example.tipee.base.BaseViewModel
import com.example.tipee.model.ProductDetail
import com.example.tipee.network.RetrofitHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductDetailViewModel : BaseViewModel() {
    private val api = RetrofitHelper.getInstance()
    private val apiTiki = RetrofitHelper.getTikiInstance()
    val mProductDetail = MutableLiveData<ProductDetail>()

    fun load(id: String){
        loadProductDetailTiki(id)
    }

    private fun loadProductDetailTiki(id: String){
        isLoading.value = true
        apiTiki.getProductDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mProductDetail.value = it
                isLoading.value = false
            }, onError)
    }
}