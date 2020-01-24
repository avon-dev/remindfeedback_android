package com.example.remindfeedback.FeedbackList.FeedbackDetail

import android.content.Context
import com.example.remindfeedback.ServerModel.CreateBoardPicture
import com.example.remindfeedback.ServerModel.CreateBoardText
import com.example.remindfeedback.ServerModel.CreateboardRecord
import com.example.remindfeedback.ServerModel.CreateboardVideo
import java.util.ArrayList

interface ContractFeedbackDetail {
    interface View{
        fun refresh()
        fun modifyBoardActivity(feedback_id: Int, board_id: Int, board_category: Int, board_title: String, board_content: String)
    }

    interface Presenter {
        var view: View
        var mContext:Context

        fun loadItems(list: ArrayList<ModelFeedbackDetail>, adapterFeedbackDetail: AdapterFeedbackDetail, feedback_Id:Int)

        fun addTextItems(list: ArrayList<ModelFeedbackDetail>,createBoardText: CreateBoardText, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun addPictureItems(list: ArrayList<ModelFeedbackDetail>,createBoardPicture: CreateBoardPicture, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun addVideoItems(list: ArrayList<ModelFeedbackDetail>,createboardVideo: CreateboardVideo, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun addRecordItems(list: ArrayList<ModelFeedbackDetail>,createboardRecord: CreateboardRecord, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun removeItems(board_id: Int, context: Context)

        fun updateTextItems(list: ArrayList<ModelFeedbackDetail>, board_id: Int, createBoardText: CreateBoardText, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun updatePictureItems(list: ArrayList<ModelFeedbackDetail>, board_id: Int, createBoardPicture: CreateBoardPicture, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun modifyBoardActivity(feedback_id: Int, board_id: Int, board_category: Int, board_title: String, board_content: String)

        fun completeRequest(feedback_id: Int)
    }

}