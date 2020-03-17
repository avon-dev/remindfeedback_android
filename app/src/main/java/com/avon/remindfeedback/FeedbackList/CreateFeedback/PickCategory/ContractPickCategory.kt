package com.avon.remindfeedback.FeedbackList.CreateFeedback.PickCategory

import android.content.Context
import java.util.ArrayList

interface ContractPickCategory {
    interface View {
        fun refresh()
        fun returnData(modelPickCategory: ModelPickCategory)
    }

    interface Presenter {

        var view: View
        var context: Context
        fun returnData(modelPickCategory: ModelPickCategory)
        fun getData(arrayData: ArrayList<ModelPickCategory>)
    }
}