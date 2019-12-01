package com.example.remindfeedback.ServerModel

//방식 바뀌어서 이거 필요없음, 혹시나 해서 안지우는중
//로그인시에 리퀘스트는 LogIn모델을 통해 요청하나 서버에서 리스폰스는 아래와 같은 형식을 통해 받아옴
data class GetToken(
    //json형식으로 accessToken클래스와 refreshToken클래스를 만들어서 받음
    val accessToken: accessToken,
    val refreshToken: refreshToken
)

data class accessToken(
    val success: Boolean,
    val message: String?,
    val error: String?,
    val data: String?
)


data class refreshToken(
    val success: Boolean,
    val message: String?,
    val error: String?,
    val data: String?
)