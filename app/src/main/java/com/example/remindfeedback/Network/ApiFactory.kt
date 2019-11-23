package com.example.remindfeedback.Network


object ApiFactory{

    val serviceAPI : ServiceAPI = RetrofitFactory.retrofit("http://54.180.118.35/")
                                                .create(ServiceAPI::class.java)

}