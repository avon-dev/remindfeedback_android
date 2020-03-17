package com.avon.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

class GetFeedback {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: List<myFeedback_List>? = null
    @SerializedName("message")
    var message: String? = ""
}


class myFeedback_List {
    @SerializedName("id")
    val id = 0
    @SerializedName("adviser_uid")
    val adviser_uid = ""
    @SerializedName("category")
    val category = 0
    @SerializedName("title")
    val title = ""
    @SerializedName("write_date")
    val write_date = ""

}