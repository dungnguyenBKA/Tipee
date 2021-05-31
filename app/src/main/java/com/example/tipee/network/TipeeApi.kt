package com.example.tipee.network

import com.example.tipee.model.BaseResponse
import com.example.tipee.model.ProductDetail
import com.example.tipee.model.response.TabItem
import com.example.tipee.screen.main.UploadImageViewModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface TipeeApi {
    @GET("products/{productId}")
    fun getProductDetail(@Path("productId") id: String) : Observable<ProductDetail>

    @GET("seller/stores/{seller}/products")
    fun getShopDetail(@Path("seller") sellerName: String) : Observable<BaseResponse<List<ProductDetail>>>

    // imgur
    @Multipart
    @POST("image")
    fun uploadImage(@Part imageBase64: MultipartBody.Part) : Observable<BaseResponse<UploadImageViewModel.ImgurRes>>

    @GET("homepage?widget_type=infinite_scroll")
    fun getHomeTab(
        @Query("tab" ) tab: Int = 0,
        @Query("offset") offSet: Int = 0
    ): Observable<TabItem>

    @GET("search")
    fun search(@Query("q" ) q: String = "") : Observable<Response<ResponseBody>>
}