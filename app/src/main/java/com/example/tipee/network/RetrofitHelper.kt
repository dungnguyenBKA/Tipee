package com.example.tipee.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {
    companion object{
        private const val TIKI_BASE_URL = "https://tiki.vn/api/v2/"
        private var retrofit : Retrofit? = null

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

        private const val TIPEE_BASE_URL = "https://tiki20201.herokuapp.com/"
        private var retrofitTipee : Retrofit? = null
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

        private const val IMGUR_BASE_URL = "https://api.imgur.com/3/"
        private var retrofitImgur : Retrofit? = null
        fun getImgurInstance(): TipeeApi{
            val interceptorLog = HttpLoggingInterceptor()
            interceptorLog.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptorLog)
                .addInterceptor(Interceptor { chain ->
                    val oriReq = chain.request()
                    val req = oriReq.newBuilder()
                        .header("Authorization", "Client-ID e87bfd62679bbf2")
                        .header("Content-Type", "multipart/form-data")
                        .method(oriReq.method, oriReq.body)
                        .build()

                    chain.proceed(req)
                })
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()
            if(retrofitImgur == null){
                retrofitImgur = Retrofit.Builder()
                    .baseUrl(IMGUR_BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofitImgur!!.create(TipeeApi::class.java)
        }
    }
}