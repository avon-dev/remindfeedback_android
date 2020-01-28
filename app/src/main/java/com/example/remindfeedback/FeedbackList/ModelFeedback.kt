package com.example.remindfeedback.FeedbackList

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

data class ModelFeedback(
    var feedback_Id: Int
    , var adviser: String //adviser_uid
    , var category: Int
    , var tagColor: String
    , var title: String
    , var feederProfileImage: String
    , var date: String?
    , var complete: Int
    , var alarm: Boolean
)
