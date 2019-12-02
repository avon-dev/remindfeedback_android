package com.example.remindfeedback.CategorySetting

import android.content.Context
import com.example.remindfeedback.FeedbackList.FeedbackDetail.Post.ModelPost
import java.util.ArrayList

interface ContextCategorySetting {
    interface View{
        fun refresh()
    }

    interface Presenter {

        var view: View

        fun loadItems(list: ArrayList<ModelCategorySetting>)

        fun addItems(title:String, mAdapter:AdapterCategorySetting)

        fun removeItems(position: Int, id: Int, context: Context)

        fun updateItems(position: Int)
    }
}