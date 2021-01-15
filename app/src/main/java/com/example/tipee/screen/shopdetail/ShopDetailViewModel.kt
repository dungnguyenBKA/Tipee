package com.example.tipee.screen.shopdetail

import androidx.lifecycle.MutableLiveData
import com.example.tipee.base.BaseViewModel
import com.example.tipee.model.ProductDetail
import com.example.tipee.network.RetrofitHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ShopDetailViewModel: BaseViewModel() {
    private val api = RetrofitHelper.getInstance()
    val mListProduct = MutableLiveData<List<ProductDetail>>()

    fun loadShopDetail(id: String){
        isLoading.value = true
        api.getShopDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mListProduct.value = it.data
                isLoading.value = false
            }, onError)
    }
}