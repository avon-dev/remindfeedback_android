package com.avon.remindfeedback.Login.FindPassword

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.avon.remindfeedback.Login.LoginActivity
import com.avon.remindfeedback.Network.RetrofitFactory
import com.avon.remindfeedback.ServerModel.*
import com.avon.remindfeedback.etcProcess.BasicDialog
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
        var preferences: SharedPreferences = context.getSharedPreferences("USERSIGN", 0)

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
                        val editor = preferences.edit()
                        editor.putString("autoLoginEmail", "")
                        editor.putString("autoLoginPw", "")
                        editor.commit()
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(intent)
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