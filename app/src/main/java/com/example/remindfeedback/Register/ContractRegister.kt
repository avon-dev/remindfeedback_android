package com.example.remindfeedback.Register

import android.content.Context

interface ContractRegister {
    interface View{

    }

    interface Presenter {

        var view: View
        var mContext:Context
        fun signup(email:String, nickname:String, password:String)

    }
}