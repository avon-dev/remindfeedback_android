package com.example.remindfeedback.FeedbackList.CreateFeedback

import android.content.Context
import android.util.Log
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import com.example.remindfeedback.CreateFeedback.ContractCreateFeedback
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.Network.retrofitFrame
import com.example.remindfeedback.ServerModel.GetAllBoard
import com.example.remindfeedback.ServerModel.GetCategory
import com.example.remindfeedback.ServerModel.myCategory_List
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PresenterCreateFeedback : ContractCreateFeedback.Presenter {


    override lateinit var view: ContractCreateFeedback.View
    override lateinit var mContext: Context


}