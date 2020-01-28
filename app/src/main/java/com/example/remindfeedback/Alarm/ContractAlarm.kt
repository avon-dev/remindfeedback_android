package com.example.remindfeedback.Alarm

import android.content.Context
import java.util.ArrayList

interface ContractAlarm {

    interface View {
        fun refresh()
    }

    interface Presenter {

        var view: View

        fun loadItems(list: ArrayList<ModelAlarm>)

        fun loadAlarm(list: ArrayList<ModelAlarm>)

        fun loadFeedback(list: ArrayList<ModelAlarm>)

        fun addItems(position: Int)

        fun removeItems(position: Int, id: Int, context: Context)

        fun updateItems(position: Int)
    }

}