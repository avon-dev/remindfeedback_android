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

    }

    interface Presenter {

        var view: View
        var mContext:Context

        fun loadItems(list: ArrayList<ModelFeedbackDetail>, adapterFeedbackDetail: AdapterFeedbackDetail, feedback_Id:Int)

        fun addTextItems(list: ArrayList<ModelFeedbackDetail>,createBoardText: CreateBoardText, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun addPictureItems(list: ArrayList<ModelFeedbackDetail>,createBoardPicture: CreateBoardPicture, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun addVideoItems(list: ArrayList<ModelFeedbackDetail>,createboardVideo: CreateboardVideo, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun addRecordItems(list: ArrayList<ModelFeedbackDetail>,createboardRecord: CreateboardRecord, adapterFeedbackDetail: AdapterFeedbackDetail)

        fun removeItems(position: Int, id: Int, context: Context)

        fun updateItems(position: Int)

    }

}