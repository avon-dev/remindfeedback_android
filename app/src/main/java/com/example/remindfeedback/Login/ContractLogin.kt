package com.example.remindfeedback.Login

interface ContractLogin {
    interface View{

    }

    interface Presenter {

        var view: View

        fun LogIn(email:String, password:String)
    }
}