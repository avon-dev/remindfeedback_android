package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

//피드백 완료요청을 수행하고 리턴된 값을 얻어오는 객체
class GetAfterCompleteRequest {
    @SerializedName("success")
    var success:Boolean = false
    @SerializedName("data")
    var data:getAfterCompleteRequest? = null
    @SerializedName("message")
    var message:String? = ""
}


class getAfterCompleteRequest{
    @SerializedName("id")
    var id:Int = -1
    @SerializedName("user_uid")
    var user_uid:String = ""
    @SerializedName("adviser_uid")
    var adviser_uid:String = ""
    @SerializedName("category")
    var category:Int = -1
    @SerializedName("title")
    var title:String = ""
    @SerializedName("write_date")
    var write_date:String = ""
    @SerializedName("complete")
    var complete:Int = -2
    @SerializedName("confirm")
    var confirm:Boolean = false
    @SerializedName("createdAt")
    var createdAt: String = ""
    @SerializedName("updatedAt")
    var updatedAt: String? = ""
    @SerializedName("deletedAt")
    var deletedAt: String? = null

}