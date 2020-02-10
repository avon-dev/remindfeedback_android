package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

class GetDeletedComment {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: getCommentData = getCommentData()
    @SerializedName("message")
    var message: String? = ""
}


class getCommentData {
    @SerializedName("board_id")
    var board_id: Int = -1
    @SerializedName("comment_id")
    var comment_id: Int = -1

}

