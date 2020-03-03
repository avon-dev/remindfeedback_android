package com.example.remindfeedback.Register

import android.content.Context

interface ContractRegister {
    interface View {

    }

    interface Presenter {

        var view: View
        var mContext: Context
        fun signUp(email: String, nickname: String, password: String, token: String)
        fun emailAuth(email: String)
    }
}