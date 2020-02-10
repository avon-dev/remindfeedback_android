package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

class GetUpdatedComment {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: getUpdatedCommentData = getUpdatedCommentData()
    @SerializedName("message")
    var message: String? = ""
}

class getUpdatedCommentData{
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
}