package com.example.remindfeedback.Network


object ApiFactory{

    val serviceAPI : ServiceAPI = RetrofitFactory.retrofit("http://93c4cc97.ngrok.io/")
                                                .create(ServiceAPI::class.java)

}