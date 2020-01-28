package com.example.remindfeedback.ServerModel

import com.google.gson.annotations.SerializedName

//show all category에 사용
class GetCategory {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("data")
    var data: List<myCategory_List>? = null
    @SerializedName("message")
    var message: String? = ""
}


class myCategory_List {
    @SerializedName("category_id")
    val category_id = 0
    @SerializedName("category_title")
    val category_title = ""
    @SerializedName("category_color")
    val category_color = "#000000"

}
