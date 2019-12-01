package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import java.util.ArrayList

class PresenterPost:ContractPost.Presenter {
    lateinit override var view: ContractPost.View


    override fun loadItems(list: ArrayList<ModelPost>) {
    }

    override fun addItems(adapterPost: AdapterPost) {
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }
}