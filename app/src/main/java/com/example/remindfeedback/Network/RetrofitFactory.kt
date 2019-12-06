package com.example.remindfeedback.Network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {

    //로그찍는 부분
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    //retrofit에 담을 client 종류 세가지중 하나를 얻어오는 코드
    fun getClient(context: Context, type: String): OkHttpClient {
        //쿠키를 추가할때 쓰는 타입
        if (type.equals("addCookie")) {
            val addCookiesInterceptor = AddCookiesInterceptor(context)
            val client = OkHttpClient.Builder()
                .addInterceptor(addCookiesInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
            return client
        }
        //쿠키를 받아서 저장할때 쓰는타입
        else if (type.equals("receiveCookie")) {
            val receivedCookiesInterceptor = ReceivedCookiesInterceptor(context)
            val client = OkHttpClient.Builder()
                .addInterceptor(receivedCookiesInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
            return client
        }
        //기타 쿠키없을때 쓰는타입 ex)회원가입
        else {
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            return client
        }

    }

    //retrofit 선언
    fun retrofit(baseUrl: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun serviceAPI(client: OkHttpClient): ServiceAPI =
        retrofit("http://54.180.118.35/", client).create(ServiceAPI::class.java)


}