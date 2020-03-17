package com.avon.remindfeedback.FriendsList.FindFriends

import android.content.Context

interface ContractFindFriends {
    interface View{

    }

    interface Presenter {

        var view: View
        var mContext:Context

        fun searchFriend(email:String)
        fun requestAddFriend(friend_uid:String)
    }

}