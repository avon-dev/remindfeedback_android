package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

class GetAllFeedback{
    @SerializedName("success")
    var success:Boolean = false
    @SerializedName("data")
    var data: getAllData? = null
    @SerializedName("message")
    var message:String? = ""
}


class getAllData {

    @SerializedName("myFeedback")
    var myFeedback: List<myFeedback>? = null
    @SerializedName("yourFeedback")
    var yourFeedback: List<yourFeedback>? = null
    @SerializedName("category")
    var category: List<category>? = null


}


class myFeedback{
    @SerializedName("id")
    val id:Int = -1
    @SerializedName("user_uid")
    val user_uid:String? = ""
    @SerializedName("adviser_uid")
    val adviser_uid:String? = ""
    @SerializedName("category")
    val category:Int = -1
    @SerializedName("title")
    val title:String = ""
    @SerializedName("write_date")
    val write_date:String? = ""
    @SerializedName("complete")
    val complete:Int = -1
    @SerializedName("confirm")
    val confirm:Boolean = false
    @SerializedName("createdAt")
    val createdAt:String? = ""
    @SerializedName("updatedAt")
    val updatedAt:String? = ""
    @SerializedName("deletedAt")
    val deletedAt:String? = null
    @SerializedName("user")
    val user:adviserUser? = adviserUser()
}

class adviserUser{
    @SerializedName("nickname")
    var nickname:String? = ""
    @SerializedName("portrait")
    var portrait:String? = ""
}

class yourFeedback{
    @SerializedName("id")
    val id:Int = -1
    @SerializedName("user_uid")
    val user_uid:String? = ""
    @SerializedName("adviser_uid")
    val adviser_uid:String? = ""
    @SerializedName("category")
    val category:Int = -1
    @SerializedName("title")
    val title:String = ""
    @SerializedName("write_date")
    val write_date:String? = ""
    @SerializedName("complete")
    val complete:Int = -1
    @SerializedName("confirm")
    val confirm:Boolean = false
    @SerializedName("createdAt")
    val createdAt:String? = ""
    @SerializedName("updatedAt")
    val updatedAt:String? = ""
    @SerializedName("deletedAt")
    val deletedAt:String? = null
    @SerializedName("user")
    val user:adviserUser? = adviserUser()
}

class category{
    @SerializedName("category_id")
    val category_id:Int = -1
    @SerializedName("category_title")
    val category_title:String? = ""
    @SerializedName("category_color")
    val category_color:String? = null
}