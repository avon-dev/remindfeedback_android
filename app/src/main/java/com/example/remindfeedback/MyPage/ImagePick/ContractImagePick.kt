package com.example.remindfeedback.MyPage.ImagePick

import android.content.Context
import android.widget.FrameLayout

interface ContractImagePick {
    interface View{

    }

    interface Presenter {
        var view: View
        var mContext: Context
    }

}