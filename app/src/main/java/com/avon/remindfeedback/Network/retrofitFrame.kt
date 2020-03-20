package com.avon.remindfeedback.Network

import android.content.Context
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class retrofitFrame(
    val mContext: Context,
    val request : Call<Object>,
    val onResponse: (json:JSONObject) -> Unit,
    val onFailure: () -> Unit
    ) {

    fun rFrame(){
        request.enqueue(object : Callback<Object> {
            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                if (response.isSuccessful) {
                    onResponse(JSONObject(Gson().toJson(response.body())))
                } else {
                }
            }
            override fun onFailure(call: Call<Object>, t: Throwable) {
                onFailure()
            }
        })
    }

}