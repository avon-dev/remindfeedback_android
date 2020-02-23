package com.example.remindfeedback.Register

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.SignUp
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterRegister : ContractRegister.Presenter {

    lateinit override var view: ContractRegister.View
    lateinit override var mContext: Context

    //회원가입 코드
    override fun signup(email: String, nickname: String, password: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "")
        val apiService = RetrofitFactory.serviceAPI(client)
        val signupclass: SignUp = SignUp(email, nickname, password)
        val register_request: Call<SignUp> = apiService.SignUp(signupclass)
        register_request.enqueue(object : Callback<SignUp> {

            override fun onResponse(call: Call<SignUp>, response: Response<SignUp>) {
                if (response.isSuccessful) {
                } else {
                    val StatusCode = response.code()
                    (mContext as Activity).finish()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
                (mContext as Activity).finish() // 회원가입 성공 후 액티비티 종료
            }

            override fun onFailure(call: Call<SignUp>, t: Throwable) {
                Log.e("회원가입 실패", t.message)

            }
        })
    }
}