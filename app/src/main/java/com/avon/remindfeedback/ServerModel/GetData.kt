package com.avon.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

//show all category에 사용
class GetData {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: String? = null
    @SerializedName("message")
    var message: String? = ""
}


