package com.example.tipee.network

import com.example.tipee.model.BaseResponse
import com.example.tipee.model.ProductDetail
import com.example.tipee.model.response.HomepageItem
import com.example.tipee.screen.main.UploadImageViewModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface TipeeApi {
    @GET("homepage")
    fun getHomepage() : Observable<List<HomepageItem>>

    // test from tiki
    @GET("products/{productId}")
    fun getProductDetail(@Path("productId") id: String) : Observable<ProductDetail>

    @GET("seller/stores/{seller}/products")
    fun getShopDetail(@Path("seller") sellerName: String) : Observable<BaseResponse<List<ProductDetail>>>

    // imgur
    @Multipart
    @POST("image")
    fun uploadImage(@Part imageBase64: MultipartBody.Part) : Observable<BaseResponse<UploadImageViewModel.ImgurRes>>
}