package com.example.remindfeedback.FeedbackList

import android.content.Context
import java.util.ArrayList

interface ContractMain {

    interface View{
        fun refresh()
    }

    interface Presenter {

        var view: View

        fun loadItems(list: ArrayList<ModelFeedback>)

        fun addItems(title: String, adapterMainFeedback: AdapterMainFeedback)

        fun removeItems(position:Int, id:Int, context: Context)

        fun updateItems(position:Int)
    }

}