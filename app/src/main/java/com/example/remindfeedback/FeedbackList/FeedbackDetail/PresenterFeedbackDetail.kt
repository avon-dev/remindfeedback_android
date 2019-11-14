package com.example.remindfeedback.FeedbackList.FeedbackDetail

import android.content.Context
import java.util.ArrayList

class PresenterFeedbackDetail:ContractFeedbackDetail.Presenter {
    lateinit override var view: ContractFeedbackDetail.View


    override fun loadItems(list: ArrayList<ModelFeedbackDetail>) {
    }

    override fun addItems(position: Int) {
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }
}