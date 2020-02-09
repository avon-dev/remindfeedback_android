package com.example.remindfeedback.Login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.remindfeedback.FeedbackList.MainActivity
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.GetMe
import com.example.remindfeedback.ServerModel.LogIn
import com.example.remindfeedback.ServerModel.RequestFindPassword
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PresenterLogin() : ContractLogin.Presenter {

    internal var preferences: SharedPreferences? = null

    lateinit override var view: ContractLogin.View
    lateinit override var mContext: Context

    override fun LogIn(email: String, password: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "receiveCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val login: LogIn = com.example.remindfeedback.ServerModel.LogIn(email, password)
        val register_request: Call<ResponseBody> = apiService.LogIn(login)
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.headers().get("Set-Cookie") == null) {
                        Toast.makeText(mContext, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        var arr = response.headers().get("Set-Cookie")!!.split(";")
                        var arr2 = arr[0].toString().split("=")
                        Log.e("getme", arr2[1])
                        getMe(arr2[1])
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

    fun getMe(mcookie: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val register_request: Call<GetMe> = apiService.GET_User()
        register_request.enqueue(object : Callback<GetMe> {

            override fun onResponse(call: Call<GetMe>, response: Response<GetMe>) {
                if (response.isSuccessful) {
                    Log.e("getuser", "여기 겟유저")
                    Log.e("response", " ${response.headers()}")

                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }

            override fun onFailure(call: Call<GetMe>, t: Throwable) {
            }
        })

        val intent = Intent(mContext, MainActivity::class.java)
        mContext.startActivity(intent)
    }


    //권한 허용해주는 부분
    override fun getPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                // 권한 요청 성공
            }
            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                // 권한 요청 실패
            }
        }
        TedPermission.with(mContext)
            .setPermissionListener(permissionListener)
            //.setRationaleMessage("사진 및 파일을 저장하기 위하여 접근 권한이 필요합니다.")
            //.setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }


}