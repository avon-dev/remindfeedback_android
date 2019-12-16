package com.example.remindfeedback.CreateFeedback

interface ContractCreateFeedback {
    interface View{
        fun setData()

    }

    interface Presenter {

        var view: View

    }

}