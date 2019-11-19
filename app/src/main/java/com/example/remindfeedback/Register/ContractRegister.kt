package com.example.remindfeedback.Register

interface ContractRegister {
    interface View{

    }

    interface Presenter {

        var view: View

        fun signup(email:String, nickname:String, password:String)

    }
}