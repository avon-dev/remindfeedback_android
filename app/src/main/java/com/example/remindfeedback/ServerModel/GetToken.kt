package com.example.remindfeedback.ServerModel

data class GetToken (
    val accessToken: accessToken,
    val refreshToken: refreshToken
)

data class accessToken (
    val success: Boolean,
    val message: String?,
    val error:String?,
    val data:String?
)


data class refreshToken(
    val success: Boolean,
    val message: String?,
    val error:String?,
    val data:String?
)