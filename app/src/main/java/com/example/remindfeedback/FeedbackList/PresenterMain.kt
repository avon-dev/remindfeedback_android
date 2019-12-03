package com.example.remindfeedback.FeedbackList

import android.content.Context
import java.util.*

class PresenterMain : ContractMain.Presenter {

    override lateinit var view: ContractMain.View

    override fun loadItems(list: ArrayList<ModelFeedback>) {

        //list.add(dict) //마지막 줄에 삽입
        view.refresh()
    }

    override fun addItems(title: String, adapterMainFeedback: AdapterMainFeedback) {

        var modelFeedback: ModelFeedback = ModelFeedback("박혜련", "black", title, "dummy", "2019.12.01 일", true)
        adapterMainFeedback.addItem(modelFeedback)

        view.refresh()
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateItems(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}