package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

class GetAllComments {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: List<getAllComment>? = null
    @SerializedName("message")
    var message: String? = ""
}


class getAllComment {
    @SerializedName("id")
    var id: Int = -1
    @SerializedName("comment_content")
    var comment_content: String = ""
    @SerializedName("confirm")
    var confirm: Boolean = false
    @SerializedName("createdAt")
    var createdAt: String = ""
    @SerializedName("updatedAt")
    var updatedAt: String? = ""
    @SerializedName("deletedAt")
    var deletedAt: String? = ""
    @SerializedName("fk_user_uid")
    var fk_user_uid: String? = ""
    @SerializedName("fk_board_id")
    var fk_board_id: Int = -1
    @SerializedName("user")
    var user: commentUser = commentUser()
}

class commentUser {
    @SerializedName("nickname")
    var nickname: String = ""
    @SerializedName("portrait")
    var portrait: String = ""
}
