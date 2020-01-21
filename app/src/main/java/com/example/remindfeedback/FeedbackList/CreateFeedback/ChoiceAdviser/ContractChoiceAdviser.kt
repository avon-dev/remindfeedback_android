package com.example.remindfeedback.FeedbackList.CreateFeedback.ChoiceAdviser

import android.content.Context
import com.example.remindfeedback.FeedbackList.AdapterMainFeedback
import com.example.remindfeedback.FeedbackList.ModelFeedback
import com.example.remindfeedback.FriendsList.AdapterFriendsList
import com.example.remindfeedback.FriendsList.ModelFriendsList
import java.util.ArrayList

interface ContractChoiceAdviser {
    interface View{
        fun refresh()

        fun returnData(user_uid:String)

    }

    interface Presenter {
        var view: View
        var context: Context

        fun loadItems(list: ArrayList<ModelFriendsList>, adapterChoiceAdviser: AdapterChoiceAdviser)

        fun returnData(user_uid:String)

    }
}