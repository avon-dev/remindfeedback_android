package com.example.remindfeedback.FriendsList.FindFriends

import android.content.Context
import com.example.remindfeedback.FriendsList.ModelFriendsList
import java.util.ArrayList

interface ContractFindFriends {
    interface View{

    }

    interface Presenter {

        var view: View
        var mContext:Context

        fun searchFriend(email:String)

    }

}