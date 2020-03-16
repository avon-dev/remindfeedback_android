package com.avon.remindfeedback.Register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.avon.remindfeedback.FeedbackList.MainActivity
import com.avon.remindfeedback.Network.RetrofitFactory
import com.avon.remindfeedback.ServerModel.*

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterRegister : ContractRegister.Presenter {

    lateinit override var view: ContractRegister.View
    lateinit override var mContext: Context

    //회원가입 코드
    override fun signup(email: String, nickname: String, password: String, token: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "")
        val apiService = RetrofitFactory.serviceAPI(client)
        val signupclass: SignUp = SignUp(email, nickname, password, token)
        val register_request: Call<GetSignUpData> = apiService.SignUp(signupclass)
        register_request.enqueue(object : Callback<GetSignUpData> {


            override fun onResponse(call: Call<GetSignUpData>, response: Response<GetSignUpData>) {
                if (response.isSuccessful) {
                    var mData = response.body()!!
                    if (mData.success) {
                        //회원가입후 바로 로그인
                        Toast.makeText(mContext, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show()
                        LogIn(email,password)
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
    override fun verify(email: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "")
        val apiService = RetrofitFactory.serviceAPI(client)
        val signupclass: sendToken = sendToken(email)
        val register_request: Call<GetData> = apiService.Verify(signupclass)
        register_request.enqueue(object : Callback<GetData> {

            override fun onResponse(call: Call<GetData>, response: Response<GetData>) {
                if (response.isSuccessful) {
                    var mData = response.body()!!
                    if (mData.success) {
                        Toast.makeText(mContext, "인증번호 전송에 성공했습니다.", Toast.LENGTH_LONG).show()
                        view.tokenSended()
                    } else {
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

    override fun LogIn(email: String, password: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "receiveCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val login: LogIn = com.avon.remindfeedback.ServerModel.LogIn(email, password)
        val register_request: Call<ResponseBody> = apiService.LogIn(login)
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.headers().get("Set-Cookie") == null) {
                        Toast.makeText(mContext, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(mContext, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        mContext.startActivity(intent)
                    }

                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("getme", "실패")
            }

        })
    }

}