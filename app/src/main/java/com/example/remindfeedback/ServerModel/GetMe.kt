package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

class GetMe {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: getMyInfo? = null
    @SerializedName("message")
    var message: String? = ""
}


class getMyInfo {
    @SerializedName("email")
    val email: String = ""
    @SerializedName("nickname")
    val nickname: String? = ""
    @SerializedName("portrait")
    val portrait: String? = ""
    @SerializedName("introduction")
    val introduction: String? = ""
    @SerializedName("tutorial")
    val tutorial: Boolean = false
}
