package com.example.remindfeedback.Network

import com.example.remindfeedback.ServerModel.*
import okhttp3.Cookie
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST


interface ServiceAPI {

    //회원가입
    @POST("auth/signup")
    fun SignUp(@Body json_account: SignUp): Call<SignUp>

    //로그인
    @POST("auth/login")
    fun LogIn( @Body login: LogIn): Call<GetToken>

    //피드백 생성
    @POST("feedback/create")
    fun CreateFeedback( @Body createFeedback: CreateFeedback): Call<CreateFeedback>

    //내정보 가져오기
    @GET("auth/me/")
    fun GET_User(): Call<ResponseBody>

    //내 피드백정보 가져오기
    @GET("feedback/all/")
    fun GetFeedback(): Call<String>
}