package com.example.remindfeedback.Login.FindPassword

import android.content.Context
import android.widget.Toast
import com.example.remindfeedback.Login.ContractLogin
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.ChangingPassword
import com.example.remindfeedback.ServerModel.RequestFindPassword
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterFindPassword: ContractFindPassword.Presenter {

    lateinit override var view: ContractFindPassword.View
    lateinit override var context: Context

    override fun findPassword(email: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val register_request: Call<ResponseBody> = apiService.RequestFindPassword(
            RequestFindPassword(email)
        )
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //Toast.makeText(context, "이메일 전송이 완료되었습니다.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "에러가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }
    override fun changingPassword(token:String, password:String){
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val register_request: Call<ResponseBody> = apiService.ChangingPassword(
           ChangingPassword(token, password)
        )
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "패스워드 수정이 완료되었습니다.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "에러가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

}