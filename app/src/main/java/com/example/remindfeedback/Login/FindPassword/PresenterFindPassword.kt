package com.example.remindfeedback.Login.FindPassword

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.remindfeedback.Login.ContractLogin
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.*
import com.example.remindfeedback.etcProcess.BasicDialog
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
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

        val register_request: Call<Object> = apiService.ChangingPassword(
           ChangingPassword(token, password)
        )
        register_request.enqueue(object : Callback<Object> {

            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                if (response.isSuccessful) {
                    var jObject: JSONObject = JSONObject(Gson().toJson(response.body()))
                    if(jObject.getBoolean("success")){
                        (context as Activity).finish()
                    }
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, "에러가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Object>, t: Throwable) {
            }
        })
    }
    override fun checkEmail(email:String){
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val register_request: Call<GetSuccessData> = apiService.CheckEmail(
            CheckingEmail(email)
        )
        register_request.enqueue(object : Callback<GetSuccessData> {

            override fun onResponse(call: Call<GetSuccessData>, response: Response<GetSuccessData>) {
                if (response.isSuccessful) {
                    var getMe:GetSuccessData = response.body()!!
                    if(getMe.success){
                        var mDialog:BasicDialog = BasicDialog(getMe.message!!, context, {(context as Activity).finish()}, {})
                        mDialog.makeDialog()
                    }else{
                        Log.e("asasda", "ASdasdasda")
                    }

                } else {
                    Toast.makeText(context, "에러가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<GetSuccessData>, t: Throwable) {
                Log.e("asasda", "ASdaswwwwwwwwwwwwdasda")
            }
        })
    }

}