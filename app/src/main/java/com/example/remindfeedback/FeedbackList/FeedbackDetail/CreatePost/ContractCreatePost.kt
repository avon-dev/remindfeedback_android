package com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import android.content.Context
import com.example.remindfeedback.ServerModel.CreateBoardText

interface ContractCreatePost {
    interface View{

    }

    interface Presenter {
        var view: View
        var mContext:Context
    }

}