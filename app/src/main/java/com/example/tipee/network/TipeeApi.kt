package com.example.tipee.network

import com.example.tipee.model.BaseResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface TipeeApi {
    @GET("")
    fun getHomepage() : Observable<BaseResponse<Any>>
}