package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName


class TestItem {

    @SerializedName("myFeedback")
    var mDatalist: List<myFeedback_List>? = null

}


class myFeedback_List {
    @SerializedName("id")
     val id = 0
    @SerializedName("user_uid")
     val user_uid = ""
    @SerializedName("adviser_uid")
     val adviser_uid = ""
    @SerializedName("category")
     val category = ""
    @SerializedName("title")
     val title = ""
    @SerializedName("write_date")
     val write_date = ""
    @SerializedName("createdAt")
     val createdAt = ""
    @SerializedName("updatedAt")
     val updatedAt = ""
    @SerializedName("deletedAt")
     val deletedAt = ""



}