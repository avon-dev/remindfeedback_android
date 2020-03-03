package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

//회원가입시에 요청하는 모델
class SignUp(var email: String, var nickname: String, var password: String, var token:String)


class GetSignUpData {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: mySignUpData? = null
    @SerializedName("message")
    var message: String? = ""
}

class mySignUpData{
    @SerializedName("user_uid")
    var user_uid: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("nickname")
    var nickname: String = ""
    @SerializedName("tutorial")
    var tutorial: Boolean = false
}

