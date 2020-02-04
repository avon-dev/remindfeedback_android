package com.example.remindfeedback.Login.FindPassword

import android.content.Context
import com.example.remindfeedback.Login.LoginActivity

interface ContractFindPassword {
    interface View{
    }

    interface Presenter {

        var view: View
        var context: Context
        fun findPassword(email:String)
        fun changingPassword(token:String, password:String)
    }
}