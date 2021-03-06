package com.avon.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName


class GetFriends {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: List<getFriendsInfo>? = null
    @SerializedName("message")
    var message: String? = ""
}

class getFriendsInfo {
    @SerializedName("id")
    var id: Int = -2
    @SerializedName("user_uid")
    var user_uid: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("nickname")
    var nickname: String? = ""
    @SerializedName("portrait")
    var portrait: String? = ""
    @SerializedName("introduction")
    var introduction: String? = ""
    @SerializedName("type")
    var type: Int = -2
}
