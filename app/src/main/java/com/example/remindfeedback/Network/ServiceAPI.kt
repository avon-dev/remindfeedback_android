package com.example.remindfeedback.Network

import com.example.remindfeedback.ServerModel.GetToken
import com.example.remindfeedback.ServerModel.LogIn
import com.example.remindfeedback.ServerModel.SignUp
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
    @GET("auth/me")
    fun GET_User(@Body login: LogIn): Call<LogIn>

}