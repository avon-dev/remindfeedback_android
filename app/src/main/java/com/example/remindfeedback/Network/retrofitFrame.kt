package com.example.remindfeedback.Network

import android.content.Context
import com.example.remindfeedback.ServerModel.GetMyPage
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class retrofitFrame(
    val mContext: Context,
    val request : Call<Object>,
    val onResponse: () -> Unit,
    val onFailure: () -> Unit
    ) {

    fun rFrame(){
        request.enqueue(object : Callback<Object> {
            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                if (response.isSuccessful) {
                    onResponse()
                } else {
                }
            }
            override fun onFailure(call: Call<Object>, t: Throwable) {
                onFailure()
            }
        })
    }

}