package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

class GetSuccessData {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: String? = ""
    @SerializedName("message")
    var message: String? = ""
}
