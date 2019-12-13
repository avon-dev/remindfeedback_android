package com.example.remindfeedback.MyPage

import android.content.Context
import android.widget.FrameLayout
import com.example.remindfeedback.FriendsList.ModelFriendsList
import java.util.ArrayList

interface ContractMyPage {
    interface View{
        fun setInfo(email:String, nickname:String, portrait:String?, introduction:String?)

    }

    interface Presenter {

        var view: View
        var mContext:Context
        fun getInfo()
        fun patchNickname(nickname: String)
        fun showDialog(showText:String, context: Context, params: FrameLayout.LayoutParams)
    }

}