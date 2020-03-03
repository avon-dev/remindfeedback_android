package com.example.remindfeedback.Register

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.RequestFindPassword
import com.example.remindfeedback.ServerModel.SignUp
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterRegister : ContractRegister.Presenter {

    lateinit override var view: ContractRegister.View
    lateinit override var mContext: Context

    //회원가입 코드
    override fun signUp(email: String, nickname: String, password: String, token: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "")
        val apiService = RetrofitFactory.serviceAPI(client)
        val signupclass: SignUp = SignUp(email, nickname, password, token)
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


    // 이메일 인증
    override fun emailAuth(email: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val register_request: Call<ResponseBody> = apiService.EmailAuth(RequestFindPassword(email))
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    Log.e("이메일 인증토큰 전송", "성공")
                } else {
                    Toast.makeText(mContext, "에러가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    Log.e("이메일 인증토큰 전송","실패")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

        })
    }

}