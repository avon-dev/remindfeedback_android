package com.avon.remindfeedback.Login.FindPassword

import android.content.Context

interface ContractFindPassword {
    interface View{
    }

    interface Presenter {

        var view: View
        var context: Context
        fun findPassword(email:String)
        fun changingPassword(token:String, password:String)
        fun checkEmail(email:String)
    }
}