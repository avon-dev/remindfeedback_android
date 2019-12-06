package com.example.remindfeedback.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.remindfeedback.FeedbackList.MainActivity
import com.example.remindfeedback.Network.*
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import com.example.remindfeedback.ServerModel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.jkyeo.splashview.SplashView
import okhttp3.Cookie
import okhttp3.ResponseBody
import okhttp3.internal.http2.Header
import org.json.JSONObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class PresenterLogin() : ContractLogin.Presenter {

    internal var preferences: SharedPreferences? = null


    lateinit override var view: ContractLogin.View
    lateinit override var mContext: Context

    override fun LogIn(email: String, password: String) {

        val client:OkHttpClient = RetrofitFactory.getClient(mContext,"receiveCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val login:LogIn = com.example.remindfeedback.ServerModel.LogIn(email, password)
        val register_request : Call<GetToken> = apiService.LogIn(login)
        register_request.enqueue(object : Callback<GetToken> {

            override fun onResponse(call: Call<GetToken>, response: Response<GetToken>) {
                if (response.isSuccessful) {
                    var arr = response.headers().get("Set-Cookie")!!.split(";")
                    var arr2 = arr[0].toString().split("=")
                    Log.e("getme", arr2[1])
                    getMe(arr2[1])

                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }
            override fun onFailure(call: Call<GetToken>, t: Throwable) {
                Log.e("getme", "실패")
            }
        })

    }



    fun getMe(mcookie:String){

        val client: OkHttpClient = RetrofitFactory.getClient(mContext,"addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request : Call<ResponseBody> = apiService.GET_User()
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.e("getuser", "여기 겟유저")
                    Log.e("response", " ${response.headers()}")

                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })

        val intent = Intent(mContext, MainActivity::class.java)
        mContext.startActivity(intent)
    }

    override fun showSplash(context: Context, activity: LoginActivity) {
        SplashView.showSplashView(activity, 3, R.drawable.logo_1, object : SplashView.OnSplashViewActionListener {
            override fun onSplashImageClick(actionUrl: String) {
                Log.d("SplashView", "img clicked. actionUrl: $actionUrl")
                Toast.makeText(context, "img clicked.", Toast.LENGTH_SHORT).show()
            }

            override fun onSplashViewDismiss(initiativeDismiss: Boolean) {
                Log.d("SplashView", "dismissed, initiativeDismiss: $initiativeDismiss")
            }
        })
    }


}