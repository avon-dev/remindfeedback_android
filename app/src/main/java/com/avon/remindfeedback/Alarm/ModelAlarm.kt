package com.avon.remindfeedback.Alarm

/*
* profileImage - 프로필 이미지
* script - 공지 내용
* date - 알림이 온지 얼마나 지낫는지
* type - 이 알림이 공지인지 피드백인지
* newAlarm - 확인했는지 안했는지
* */
class ModelAlarm(
    var profileImage: String,
    var script: String,
    var date: String,
    var type: String,
    var newAlarm: Boolean
)