package com.avon.remindfeedback.FeedbackList.CreateFeedback.ChoiceAdviser

import android.content.Context
import com.avon.remindfeedback.FriendsList.ModelFriendsList
import java.util.ArrayList

interface ContractChoiceAdviser {
    interface View {
        fun refresh()

        fun returnData(user_uid: String,portrait:String, introduction:String, nickname:String)

    }

    interface Presenter {
        var view: View
        var context: Context

        fun loadItems(list: ArrayList<ModelFriendsList>, adapterChoiceAdviser: AdapterChoiceAdviser)

        fun returnData(user_uid: String, portrait:String, introduction:String, nickname:String)

    }
}