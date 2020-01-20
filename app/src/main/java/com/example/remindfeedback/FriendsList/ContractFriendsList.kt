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
        fun loadItems(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList)

        //받은 친구요청
        fun receivedFriendRequests(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList)

        //보낸 친구요청
        fun requestedFriendsRequests(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList)

        fun addItems(email:String, adapterFriendsList: AdapterFriendsList)

        fun removeItems(position:Int, id:Int, context: Context)

        fun updateItems(position:Int)

        fun acceptRequest(list: ArrayList<ModelFriendsList>,user_uid:String, adapterFriendsList: AdapterFriendsList)

        fun rejectRequest(list: ArrayList<ModelFriendsList>,user_uid:String, adapterFriendsList: AdapterFriendsList)
    }

}