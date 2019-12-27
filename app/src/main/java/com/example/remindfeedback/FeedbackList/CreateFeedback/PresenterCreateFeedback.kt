package com.example.remindfeedback.FeedbackList.CreateFeedback

import android.content.Context
import android.util.Log
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import com.example.remindfeedback.CreateFeedback.ContractCreateFeedback
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.GetCategory
import com.example.remindfeedback.ServerModel.myCategory_List
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterCreateFeedback : ContractCreateFeedback.Presenter {


    override lateinit var view: ContractCreateFeedback.View
    override lateinit var mContext: Context


}