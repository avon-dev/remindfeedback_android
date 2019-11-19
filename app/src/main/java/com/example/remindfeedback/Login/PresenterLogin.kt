package com.example.remindfeedback.Login

import android.util.Log
import com.example.remindfeedback.Network.ApiFactory
import com.example.remindfeedback.ServerModel.LogIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterLogin: ContractLogin.Presenter {


    lateinit override var view: ContractLogin.View

    override fun LogIn(email: String, password: String) {
        val apiService = ApiFactory.serviceAPI
        val login:LogIn = com.example.remindfeedback.ServerModel.LogIn(email, password)
        val register_request : Call<LogIn> = apiService.LogIn(login)
        register_request.enqueue(object : Callback<LogIn> {

            override fun onResponse(call: Call<LogIn>, response: Response<LogIn>) {
                if (response.isSuccessful) {
                    Log.e("성공시", response.body().toString())

                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }
            override fun onFailure(call: Call<LogIn>, t: Throwable) {
            }
        })
    }
}