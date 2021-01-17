package com.example.tipee.network

import com.example.tipee.model.BaseResponse
import com.example.tipee.model.ProductDetail
import com.example.tipee.model.response.HomepageItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TipeeApi {
    @GET("homepage")
    fun getHomepage() : Observable<List<HomepageItem>>

    @GET("products/{productId}")
    fun getProductDetail(@Path("productId") id: String) : Observable<ProductDetail>

    @GET("seller/stores/{seller}/products")
    fun getShopDetail(@Path("seller") sellerName: String) : Observable<BaseResponse<List<ProductDetail>>>
}