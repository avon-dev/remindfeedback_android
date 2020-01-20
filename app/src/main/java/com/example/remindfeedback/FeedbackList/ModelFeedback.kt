package com.example.remindfeedback.FeedbackList

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

/*
* name - 사용자 이름
* tagColor - 피드백 구분 태그의 색상 //컬러 넘버로 숫자를 줄수도 있고 컬러 이름을 String으로 받을수도 있지만 일단 String
* script - 피드백 제목
* profileImage - 프로필이미지 일단은 String으로 해놓음
* date - 피드백 작성 날짜?
* alarm - 새로운 알람이 있는지 없는지 Boolean값으로 받아서 표시해줌
* */
@SuppressLint("ParcelCreator")
data class ModelFeedback (var feedback_Id: Int
                          , var adviser:String
                          , var category:Int
                          , var tagColor:String
                          , var title:String
                          , var feederProfileImage:String
                          , var date:String?
                          , var alarm:Boolean): Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}