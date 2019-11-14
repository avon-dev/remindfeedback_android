package com.example.remindfeedback.FeedbackList.FeedbackDetail

import android.content.Context
import com.example.remindfeedback.Alarm.ModelAlarm
import java.util.ArrayList

interface ContractFeedbackDetail {
    interface View{
        fun refresh()
    }

    interface Presenter {

        var view: View

        fun loadItems(list: ArrayList<ModelFeedbackDetail>)

        fun addItems(position: Int)

        fun removeItems(position: Int, id: Int, context: Context)

        fun updateItems(position: Int)
    }

}