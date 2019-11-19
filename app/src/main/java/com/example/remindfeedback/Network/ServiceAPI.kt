package com.example.remindfeedback.Network

import retrofit2.Call
import retrofit2.http.*

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


    @POST("auth/signup")
    fun SignUp(@Body json_account: SignUp): Call<SignUp>

}