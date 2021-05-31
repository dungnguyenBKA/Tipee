package com.example.tipee.screen.productdetail

import com.example.tipee.base.BaseViewModel
import com.example.tipee.model.ProductDetail
import com.example.tipee.model.Review
import com.example.tipee.network.RetrofitHelper
import com.example.tipee.network.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductDetailViewModel : BaseViewModel() {
    private val apiTiki = RetrofitHelper.getTikiInstance()
    val mProductDetail = SingleLiveEvent<ProductDetail>()

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

    val productReviews = SingleLiveEvent<List<Review>>()
    fun loadReview(id: String){
        isLoading.value = true
        apiTiki.getReviews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                productReviews.value = it.data
                isLoading.value = false
            }, onError)
    }
}