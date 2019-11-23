package com.example.remindfeedback.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.remindfeedback.FeedbackList.MainActivity
import com.example.remindfeedback.Network.ApiFactory
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.GetToken
import com.example.remindfeedback.ServerModel.LogIn
import com.example.remindfeedback.ServerModel.accessToken
import com.example.remindfeedback.ServerModel.refreshToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.jkyeo.splashview.SplashView
import org.json.JSONObject


class PresenterLogin() : ContractLogin.Presenter {

    internal var preferences: SharedPreferences? = null


    lateinit override var view: ContractLogin.View
    lateinit override var mContext: Context

    override fun LogIn(email: String, password: String) {
        val apiService = ApiFactory.serviceAPI
        val login:LogIn = com.example.remindfeedback.ServerModel.LogIn(email, password)
        val register_request : Call<GetToken> = apiService.LogIn(login)
        register_request.enqueue(object : Callback<GetToken> {

            override fun onResponse(call: Call<GetToken>, response: Response<GetToken>) {
                if (response.isSuccessful) {
                    //서버로부터 넘어온 데이터에서 accessToken과 refreshToken을 추출함
                    val getToken:GetToken = response.body()!!
                    val myAaccessToken:accessToken = getToken.accessToken
                    val myRefreshToken:refreshToken = getToken.refreshToken
                    Log.e("accessToken", "Code : ${myAaccessToken.data}")
                    Log.e("refreshToken", "Code : ${myRefreshToken.data}")
                    saveData(myAaccessToken.data!!, myRefreshToken.data!!)

                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }
            override fun onFailure(call: Call<GetToken>, t: Throwable) {
            }
        })
    }

    fun saveData(accessToken: String, refreshToken: String){
        preferences = mContext.getSharedPreferences("USERSIGN", 0)
        val editor = preferences!!.edit()
        editor.putString("aToken", accessToken)
        editor.putString("rToken", refreshToken)
        editor.apply()
        //데이터를 저장했으니 메인화면으로 이동
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