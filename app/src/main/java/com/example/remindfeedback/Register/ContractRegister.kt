package com.example.remindfeedback.Register

import android.content.Context

interface ContractRegister {
    interface View{
        fun tokenSended()

    }

    interface Presenter {

        var view: View
        var mContext:Context
        fun signup(email:String, nickname:String, password:String, token:String)
        fun verify(email:String)

    }
}