package com.example.remindfeedback.Login

import android.content.Context

interface ContractLogin {
    interface View{
    }

    interface Presenter {

        var view: View
        var mContext:Context
        fun LogIn(email:String, password:String)
        fun showSplash(context: Context, activity: LoginActivity)
    }
}