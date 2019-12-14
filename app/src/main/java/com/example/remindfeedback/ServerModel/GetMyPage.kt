package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject

class GetMyPage{
    @SerializedName("success")
    var success:Boolean = false
    @SerializedName("data")
    var data: myPage_Data? = null
    @SerializedName("message")
    var message:String? = ""
}


class myPage_Data {
    @SerializedName("email")
    val email:String? = ""
    @SerializedName("nickname")
    val nickname = ""
    @SerializedName("portrait")
    val portrait:String? = ""
    @SerializedName("introduction")
    val introduction:String? = ""

}