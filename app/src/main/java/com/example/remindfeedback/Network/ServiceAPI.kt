package com.example.remindfeedback.Network

import com.example.remindfeedback.ServerModel.LogIn
import com.example.remindfeedback.ServerModel.SignUp
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST


interface ServiceAPI {

/*
    이안에 내용은 신경 안써도 ㅇㅋ
    @GET("url/")
    fun getPosts() : Deferred<Response<List<Json_Account_Load>>>

    @POST("url/")
     fun post_Data_Json_Account(@Query("format") json: String, @Body json_account: Json_Account): Call<Json_Account>

    @FormUrlEncoded
    @PATCH("url/")
     fun patch_Name(@Path("pk") pk: Int, @Query("format") json: String, @Field("name") name: String): Call<ResponseBody>

    @DELETE("url/")
     fun delete_Data(@Path("path") rest: String, @Path("pk") pk: Int, @Query("format") json: String): Call<ResponseBody>

   */
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
    fun LogIn( @Body login: LogIn): Call<LogIn>
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