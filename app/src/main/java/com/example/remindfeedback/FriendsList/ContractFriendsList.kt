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
        //친구목록 불러오기
        fun loadItems(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList)

        //받은 친구요청 불러오기
        fun receivedFriendRequests(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList)

        //보낸 친구요청 불러오기
        fun requestedFriendsRequests(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList)

        fun loadBlockedFriends(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList)

        //친구 추가
        fun addItems(email:String, adapterFriendsList: AdapterFriendsList)

        fun removeItems(position:Int, id:Int, context: Context)

        fun updateItems(position:Int)
        //친구요청 수락
        fun acceptRequest(list: ArrayList<ModelFriendsList>,user_uid:String, adapterFriendsList: AdapterFriendsList)
        //친구요청 거절
        fun rejectRequest(list: ArrayList<ModelFriendsList>,user_uid:String, adapterFriendsList: AdapterFriendsList)
        //친구 차단
        fun blockRequest(list: ArrayList<ModelFriendsList>,user_uid:String, adapterFriendsList: AdapterFriendsList)
        //친구 차단 해제
        fun unBlockRequest(list: ArrayList<ModelFriendsList>,user_uid:String, adapterFriendsList: AdapterFriendsList)

    }

}