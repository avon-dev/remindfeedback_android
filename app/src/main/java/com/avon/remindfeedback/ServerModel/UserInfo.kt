package com.avon.remindfeedback.ServerModel

class UserInfo(
    var id: Int,
    var user_uid: String,
    var email: String,
    var nickname: String,
    var password: String,
    var portrait: String,
    var introduction: String,
    var create_date: String,
    var update_date: String,
    var isDeleted: Boolean
)