package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import com.example.remindfeedback.FeedbackList.FeedbackDetail.ModelFeedbackDetail
import com.example.remindfeedback.ServerModel.CreateComment
import java.util.ArrayList

interface ContractPost {

    interface View{
        fun refresh()
        fun setView(contentsType:Int,fileUrl_1:String?, fileUrl_2:String?, fileUrl_3:String?)
        fun viewPagerSetting()

    }

    interface Presenter {

        var view: View
        var mContext:Context

        fun getComment(list: ArrayList<ModelComment>, adapterPost: AdapterPost, board_id: Int)

        fun addComment(adapterPost: AdapterPost, createComment: CreateComment, arrayList: ArrayList<ModelComment>)

        fun removeItems(position: Int, id: Int, context: Context)

        fun updateItems(position: Int)

        fun typeInit(feedback_id:Int, board_id:Int)
    }
}