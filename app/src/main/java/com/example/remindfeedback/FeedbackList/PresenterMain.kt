package com.example.remindfeedback.FeedbackList

import android.content.Context
import android.util.Log
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import java.util.ArrayList

class PresenterMain : ContractMain.Presenter {

    lateinit override var view: ContractMain.View

    override fun loadItems(list: ArrayList<ModelFeedback>) {

    }

    override fun addItems(title:String, adapterMainFeedback: AdapterMainFeedback) {
        var modelFeedback:ModelFeedback = ModelFeedback("박혜련", "", title,"dummy", "12월 03일",true)
        adapterMainFeedback.addItem(modelFeedback)
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateItems(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




}