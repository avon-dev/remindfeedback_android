package com.example.remindfeedback.Register

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.GetData
import com.example.remindfeedback.ServerModel.GetSignUpData

import com.example.remindfeedback.ServerModel.SignUp
import com.example.remindfeedback.ServerModel.sendToken
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterRegister : ContractRegister.Presenter {

    lateinit override var view: ContractRegister.View
    lateinit override var mContext: Context

    //회원가입 코드
    override fun signup(email: String, nickname: String, password: String, token:String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "")
        val apiService = RetrofitFactory.serviceAPI(client)
        val signupclass: SignUp = SignUp(email, nickname, password,token)
        val register_request: Call<GetSignUpData> = apiService.SignUp(signupclass)
        register_request.enqueue(object : Callback<GetSignUpData> {


            override fun onResponse(call: Call<GetSignUpData>, response: Response<GetSignUpData>) {
                if (response.isSuccessful) {
                    var mData = response.body()!!
                    if(mData.success){
                        (mContext as Activity).finish() // 회원가입 성공 후 액티비티 종료
                    }
                    Toast.makeText(mContext, mData.message, Toast.LENGTH_LONG).show()

                } else {
                    val StatusCode = response.code()
                    (mContext as Activity).finish()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())

            }

            override fun onFailure(call: Call<GetSignUpData>, t: Throwable) {
                Log.e("회원가입 실패", t.message)

            }
        })
    }

    //이메일인증
    override fun verify(email:String){
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "")
        val apiService = RetrofitFactory.serviceAPI(client)
        val signupclass: sendToken = sendToken(email)
        val register_request: Call<GetData> = apiService.Verify(signupclass)
        register_request.enqueue(object : Callback<GetData> {

            override fun onResponse(call: Call<GetData>, response: Response<GetData>) {
                if (response.isSuccessful) {
                    var mData = response.body()!!
                    if(mData.success){
                        Toast.makeText(mContext, "인증번호 전송에 성공했습니다.", Toast.LENGTH_LONG).show()
                        view.tokenSended()
                    }else{
                        Toast.makeText(mContext, mData.message, Toast.LENGTH_LONG).show()
                    }

                } else {
                    val StatusCode = response.code()
                    //(mContext as Activity).finish()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
                //(mContext as Activity).finish() // 회원가입 성공 후 액티비티 종료
            }

            override fun onFailure(call: Call<GetData>, t: Throwable) {
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