package com.example.tipee.network

import com.example.tipee.model.BaseResponse
import com.example.tipee.model.ProductDetail
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TipeeApi {
    @GET("")
    fun getHomepage() : Observable<BaseResponse<Any>>

    @GET("products/{productId}")
    fun getProductDetail(@Path("productId") id: String) : Observable<ProductDetail>
}