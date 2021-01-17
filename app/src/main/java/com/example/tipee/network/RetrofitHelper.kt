package com.example.tipee.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {
    companion object{
        private const val TIKI_BASE_URL = "https://tiki.vn/api/v2/"
        private const val TIPEE_BASE_URL = "https://tiki20201.herokuapp.com/"
        private var retrofit : Retrofit? = null
        private var retrofitTipee : Retrofit? = null
        fun getTikiInstance(): TipeeApi{
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            if(retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl(TIKI_BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(TipeeApi::class.java)
        }

        fun getInstance(): TipeeApi{
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
            if(retrofitTipee == null){
                retrofitTipee = Retrofit.Builder()
                    .baseUrl(TIPEE_BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofitTipee!!.create(TipeeApi::class.java)
        }
    }
}