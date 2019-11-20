package com.example.remindfeedback.Login

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.remindfeedback.Network.ApiFactory
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.LogIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.jkyeo.splashview.SplashView


class PresenterLogin: ContractLogin.Presenter {



    lateinit override var view: ContractLogin.View

    override fun LogIn(email: String, password: String) {
        val apiService = ApiFactory.serviceAPI
        val login:LogIn = com.example.remindfeedback.ServerModel.LogIn(email, password)
        val register_request : Call<LogIn> = apiService.LogIn(login)
        register_request.enqueue(object : Callback<LogIn> {

            override fun onResponse(call: Call<LogIn>, response: Response<LogIn>) {
                if (response.isSuccessful) {

                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }
            override fun onFailure(call: Call<LogIn>, t: Throwable) {
            }
        })
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