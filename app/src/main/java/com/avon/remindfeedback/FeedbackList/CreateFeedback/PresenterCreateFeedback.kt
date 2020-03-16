package com.avon.remindfeedback.FeedbackList.CreateFeedback

import android.content.Context
import android.util.Log
import com.avon.remindfeedback.CreateFeedback.ContractCreateFeedback
import com.avon.remindfeedback.Network.RetrofitFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterCreateFeedback : ContractCreateFeedback.Presenter {


    override lateinit var view: ContractCreateFeedback.View
    override lateinit var mContext: Context

    override fun getOneCategory(category_id: Int) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<Object> =
            apiService.GetOneCategory(category_id)
        register_request.enqueue(object : Callback<Object> {

            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                if (response.isSuccessful) {
                    var jObject: JSONObject = JSONObject(Gson().toJson(response.body()))
                    if(jObject.getBoolean("success")){
                        var mData = jObject.getJSONObject("data")
                        view.setColor(mData.getString("category_title"), mData.getString("category_color"))
                    }


                }
            }

            override fun onFailure(call: Call<Object>, t: Throwable) {
                Log.e("피드백 수정 실패", t.message)
            }

        })
    }


}