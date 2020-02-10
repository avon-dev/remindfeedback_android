package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.widget.FrameLayout
import com.example.remindfeedback.FeedbackList.FeedbackDetail.ModelFeedbackDetail
import com.example.remindfeedback.ServerModel.CreateComment
import com.example.remindfeedback.etcProcess.MyProgress
import java.util.ArrayList

interface ContractPost {

    interface View {
        fun refresh()
        fun setView(contentsType: Int, fileUrl_1: String?, fileUrl_2: String?, fileUrl_3: String?, title: String, date: String, content: String)

        fun viewPagerSetting()

        fun setCommentId(comment_id:Int)

        fun loadItem()
    }

    interface Presenter {

        var view: View
        var mContext: Context

        fun getComment(list: ArrayList<ModelComment>, adapterPost: AdapterPost, board_id: Int, last_id:Int)

        fun addComment(adapterPost: AdapterPost, createComment: CreateComment, arrayList: ArrayList<ModelComment>)

        fun removeItems(comment_id: Int, list: ArrayList<ModelComment>, adapterPost: AdapterPost, board_id:Int)

        fun updateItems(comment_id: Int, adapterPost: AdapterPost, curruntScript:String,list: ArrayList<ModelComment>, board_id:Int)

        fun typeInit(feedback_id: Int, board_id: Int)

        fun showDialog(comment_id: Int, adapterPost: AdapterPost, curruntScript:String,showText: String, params: FrameLayout.LayoutParams,list: ArrayList<ModelComment>, board_id:Int)
    }
}