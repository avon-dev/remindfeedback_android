package com.example.remindfeedback.MyPage

import android.content.Context
import com.example.remindfeedback.FriendsList.ModelFriendsList
import java.util.ArrayList

interface ContractMyPage {
    interface View{

    }

    interface Presenter {

        var view: View


    }

}