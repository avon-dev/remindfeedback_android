package com.example.remindfeedback.FriendsList

import android.content.Context
import com.example.remindfeedback.FeedbackList.ModelFeedback
import java.util.ArrayList

interface ContractFriendsList {

    interface View{
        fun refresh()
    }

    interface Presenter {

        var view: View
        var context:Context
        fun loadItems(list: ArrayList<ModelFriendsList>)

        fun addItems(email:String, adapterFriendsList: AdapterFriendsList)

        fun removeItems(position:Int, id:Int, context: Context)

        fun updateItems(position:Int)
    }

}