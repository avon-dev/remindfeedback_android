package com.example.remindfeedback.Network

import com.example.remindfeedback.ServerModel.GetToken
import com.example.remindfeedback.ServerModel.LogIn
import com.example.remindfeedback.ServerModel.SignUp
import okhttp3.Cookie
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST


interface ServiceAPI {


/*
    @Multipart
    @POST("auth/signup")
    fun SignUp(
        @Part("email") name:RequestBody,
        @Part("nickname") email:RequestBody,
        @Part("password") country:RequestBody
    ): Call<RequestBody>
*/

    //회원가입
    @POST("auth/signup")
    fun SignUp(@Body json_account: SignUp): Call<SignUp>

    //로그인
    @POST("auth/login")
    fun LogIn( @Body login: LogIn): Call<GetToken>


/*
    //로그인
    @POST("auth/login")
    fun LogIn(@Header("Authorization") token: String, @Body login: LogIn): Call<LogIn>
   */
/*
    @FormUrlEncoded
    @POST("/auth/token")
    fun getToken(
        @Header("Authorization") token: String, @Field("phone") phone: String,
        @Field("email") email: String, @Field("password") password: String
    ): Call<Map<String, String>>
*/
/*
    @POST("auth/me")
    fun GET_User( @Field("cookie") cookie: String): Call<ResponseBody>
*/

    //바디는 폼이나 멀티파트를 사용할 수 없음

    /*
    @GET("auth/me/")
    fun GET_User(@Query("connect.sid") cookie:String ): Call<ResponseBody>

*/
    @GET("auth/me/")
    fun GET_User(): Call<ResponseBody>
}