package com.example.remindfeedback.MyPage

import android.content.Context
import android.util.Log
import com.example.remindfeedback.FriendsList.ContractFriendsList
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.GetMyPage
import com.example.remindfeedback.ServerModel.LogIn
import com.example.remindfeedback.ServerModel.myPage_Data
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterMyPage : ContractMyPage.Presenter{


    lateinit override var view: ContractMyPage.View
    lateinit override var mContext: Context
    override fun getInfo() {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext,"addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request : Call<GetMyPage> = apiService.GetMyPage()
        register_request.enqueue(object : Callback<GetMyPage> {
            override fun onResponse(call: Call<GetMyPage>, response: Response<GetMyPage>) {
                if (response.isSuccessful) {
                    //데이터 얻어서 activity로 보내줌
                    val getMyPage:GetMyPage = response.body()!!
                    val info  = getMyPage.data
                    view.setInfo(info!!.email!!,info.nickname, info.portrait!!, info.introduction!! )
                } else {
                    val StatusCode = response.code()
                }
            }
            override fun onFailure(call: Call<GetMyPage>, t: Throwable) {
            }
        })

    }
}