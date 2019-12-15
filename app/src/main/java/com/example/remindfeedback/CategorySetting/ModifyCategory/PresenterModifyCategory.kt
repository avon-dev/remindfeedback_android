package com.example.remindfeedback.CategorySetting.ModifyCategory

import android.content.Context
import android.util.Log
import com.example.remindfeedback.CategorySetting.ContractCategorySetting
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.CreateCategory
import com.example.remindfeedback.ServerModel.GetCategory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterModifyCategory:ContractModifyCategory.Presenter {

    override lateinit var view: ContractModifyCategory.View
    override lateinit var context: Context

    override fun updateItems(id: Int, color: String, title: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var modifyCategory: CreateCategory = CreateCategory(title, color)
        val register_request: Call<GetCategory> = apiService.ModifyCategory(id, modifyCategory)
        register_request.enqueue(object : Callback<GetCategory> {

            override fun onResponse(call: Call<GetCategory>, response: Response<GetCategory>) {
                if (response.isSuccessful) {
                    Log.e("성공", "수정 성공")
                }
            }

            override fun onFailure(call: Call<GetCategory>, t: Throwable) {
                Log.e("실패", t.message)
            }

        })
    }
}