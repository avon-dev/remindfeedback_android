package com.example.remindfeedback.Login

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.remindfeedback.FeedbackList.MainActivity
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.LogIn
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class PresenterLogin() : ContractLogin.Presenter {

    internal var preferences: SharedPreferences? = null

    lateinit override var view: ContractLogin.View
    lateinit override var mContext: Context

    //퍼미션 응답 처리 코드
    private val multiplePermissionsCode = 100
    //필요한 퍼미션 리스트
    //원하는 퍼미션을 이곳에 추가하면 된다.
    private val requiredPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)

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
                        //var arr = response.headers().get("Set-Cookie")!!.split(";")
                        //var arr2 = arr[0].toString().split("=")
                        getMe(email, password)
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

    fun getMe(email: String, password: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val register_request: Call<Object> = apiService.GET_User()
        register_request.enqueue(object : Callback<Object> {

            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                if (response.isSuccessful) {
                } else {
                    val StatusCode = response.code()
                }
            }
            override fun onFailure(call: Call<Object>, t: Throwable) {
            }
        })
        preferences = mContext.getSharedPreferences("USERSIGN", 0)
        val editor = preferences!!.edit()
        editor.putString("autoLoginEmail", email)
        editor.putString("autoLoginPw", password)
        editor.commit()
        val intent = Intent(mContext, MainActivity::class.java)
        mContext.startActivity(intent)
        (mContext as Activity).finish()
    }


    //권한 허용해주는 부분
    override fun getPermission() {
        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면...
        if(rejectedPermissionList.isNotEmpty()){
            //권한 요청!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions((mContext as Activity) , rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }

    //자동로그인
    override fun isSignin() {
        preferences = mContext.getSharedPreferences("USERSIGN", 0)
        val isLoginEmail: String = preferences!!.getString("autoLoginEmail", "")!!;
        val isLoginPw: String = preferences!!.getString("autoLoginPw", "")!!;
        if(!isLoginEmail.equals("")){//로그인 되어있는 상태라면
            LogIn(isLoginEmail,isLoginPw ) //로그인
        }
    }


}