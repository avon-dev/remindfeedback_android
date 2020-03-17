package com.avon.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

//로그인시에 요청하는 모델
class LogIn (var email:String, var password:String)

data class sendToken(
    @SerializedName("email")
    var email: String
)