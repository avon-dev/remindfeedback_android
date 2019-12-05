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

    fun getClient(context: Context, type: String): OkHttpClient {

        if (type.equals("addCookie")) {
            val addCookiesInterceptor = AddCookiesInterceptor(context)
            val client = OkHttpClient.Builder()
                .addInterceptor(addCookiesInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
            return client
        } else if (type.equals("receiveCookie")) {
            val receivedCookiesInterceptor = ReceivedCookiesInterceptor(context)
            val client = OkHttpClient.Builder()
                .addInterceptor(receivedCookiesInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
            return client
        } else {
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