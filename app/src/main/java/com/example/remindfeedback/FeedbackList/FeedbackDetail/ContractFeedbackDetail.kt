package com.example.remindfeedback.FeedbackList.FeedbackDetail

import android.content.Context
import com.example.remindfeedback.Alarm.ModelAlarm
import com.example.remindfeedback.ServerModel.CreateBoardPicture
import com.example.remindfeedback.ServerModel.CreateBoardText
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

        fun removeItems(position: Int, id: Int, context: Context)

        fun updateItems(position: Int)

    }

}