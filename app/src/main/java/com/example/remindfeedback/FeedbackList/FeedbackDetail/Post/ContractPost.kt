package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import com.example.remindfeedback.FeedbackList.FeedbackDetail.ModelFeedbackDetail
import java.util.ArrayList

interface ContractPost {

    interface View{
        fun refresh()
    }

    interface Presenter {

        var view: View

        fun loadItems(list: ArrayList<ModelPost>)

        fun addItems(adapterPost: AdapterPost, comment:String)

        fun removeItems(position: Int, id: Int, context: Context)

        fun updateItems(position: Int)
    }
}