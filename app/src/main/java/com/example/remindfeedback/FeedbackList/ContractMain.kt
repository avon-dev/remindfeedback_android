package com.example.remindfeedback.FeedbackList

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import java.util.*

interface ContractMain {
    interface View{
        fun refresh()
        fun modifyFeedbackActivity(id:Int,category_id: Int, date: String?, title: String)
    }

    interface Presenter {
        var view: View
        var context:Context

        fun loadItems(list: ArrayList<ModelFeedback?>, adapterMainFeedback: AdapterMainFeedback,feedback_count:Int)

        fun addItems(list: ArrayList<ModelFeedback?>,category_id: Int, date: String?, title:String,color:String, adapterMainFeedback: AdapterMainFeedback)

        fun removeItems(id:Int, context: Context)

        fun updateItems(list: ArrayList<ModelFeedback?>,item_id:Int,category_id: Int, date: String?, title: String,color:String, adapterMainFeedback: AdapterMainFeedback)

        fun modifyFeedbackActivity(id:Int, category_id: Int, date: String?, title: String)
    }

}