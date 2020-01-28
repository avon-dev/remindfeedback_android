package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName
import java.util.*


class GetAllBoard {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: List<getAllBoardData>? = null
    @SerializedName("message")
    var message: String? = ""
}

class getAllBoardData {
    @SerializedName("id")
    var id: Int = -1
    @SerializedName("board_category")
    var board_category: Int = -1
    @SerializedName("board_title")
    var board_title: String = ""
    @SerializedName("board_content")
    var board_content: String = ""
    @SerializedName("board_file1")
    var board_file1: String? = ""
    @SerializedName("board_file2")
    var board_file2: String? = ""
    @SerializedName("board_file3")
    var board_file3: String? = ""
    @SerializedName("confirm")
    var confirm: Boolean = false
    @SerializedName("createdAt")
    var createdAt: String = ""
    @SerializedName("updatedAt")
    var updatedAt: String? = ""
    @SerializedName("deletedAt")
    var deletedAt: String? = ""
    @SerializedName("fk_feedbackId")
    var fk_feedbackId: Int = -1
}
