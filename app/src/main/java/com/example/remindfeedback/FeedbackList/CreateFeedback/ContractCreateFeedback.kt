package com.example.remindfeedback.CreateFeedback

import android.content.Context
import org.jetbrains.annotations.Contract

interface ContractCreateFeedback {
    interface View{
        fun setData()

    }

    interface Presenter {

        var view: View
        var mContext:Context

        fun getCategoryData(colorArray:ArrayList<String>, titleArray:ArrayList<String>, idArray:ArrayList<Int>)
    }

}