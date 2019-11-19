package com.example.remindfeedback.Register

import android.util.Log
import com.example.remindfeedback.Network.ApiFactory
import com.example.remindfeedback.ServerModel.SignUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterRegister: ContractRegister.Presenter {

    lateinit override var view: ContractRegister.View

    //회원가입 코드
    override fun signup(email: String, nickname: String, password: String) {
        val apiService = ApiFactory.serviceAPI
        val signupclass: SignUp =
            SignUp(email, nickname, password)
        val register_request : Call<SignUp> = apiService.SignUp(signupclass)
        register_request.enqueue(object : Callback<SignUp> {

            override fun onResponse(call: Call<SignUp>, response: Response<SignUp>) {
                if (response.isSuccessful) {
                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }
            override fun onFailure(call: Call<SignUp>, t: Throwable) {
            }
        })
    }
}