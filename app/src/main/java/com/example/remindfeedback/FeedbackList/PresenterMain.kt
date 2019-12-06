package com.example.remindfeedback.FeedbackList

import android.content.Context
import android.util.Log
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import com.example.remindfeedback.FriendsList.ModelFriendsList
import com.example.remindfeedback.Network.AddCookiesInterceptor
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.Network.ServiceAPI
import com.example.remindfeedback.ServerModel.CreateFeedback
import com.example.remindfeedback.ServerModel.GetFeedback
import com.example.remindfeedback.ServerModel.TestItem
import com.example.remindfeedback.ServerModel.myFeedback_List
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class PresenterMain : ContractMain.Presenter {

    lateinit override var view: ContractMain.View
    lateinit override var context: Context


    
    override fun loadItems(list: ArrayList<ModelFeedback>, adapterMainFeedback: AdapterMainFeedback) {
        val client: OkHttpClient = RetrofitFactory.getClient(context,"addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request : Call<TestItem> = apiService.GetFeedback()
        register_request.enqueue(object : Callback<TestItem> {

            override fun onResponse(call: Call<TestItem>, response: Response<TestItem>) {
                if (response.isSuccessful) {
                    val testItem : TestItem = response.body()!!
                    val aaaa = testItem.mDatalist
                    if (aaaa != null) {
                        for (i in 0 until aaaa.size) {
                            var mfl : myFeedback_List = myFeedback_List()
                            mfl = aaaa[i]
                            var addData : ModelFeedback = ModelFeedback(mfl.adviser_uid, mfl.category, mfl.title, "dummy", mfl.createdAt, false)
                            adapterMainFeedback.addItem(addData)
                            view.refresh()
                        }
                    }else{
                    }

                } else {
                }
                Log.e("tag", "response=" + response.raw())
            }
            override fun onFailure(call: Call<TestItem>, t: Throwable) {
            }
        })

    }

    override fun addItems(title:String, adapterMainFeedback: AdapterMainFeedback) {

        // 현재시간을 msec 으로 구한다.
        val now = System.currentTimeMillis()
        // 현재시간을 date 변수에 저장한다.
        val date = Date(now)
        val client: OkHttpClient = RetrofitFactory.getClient(context,"addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var createFeedback:CreateFeedback = CreateFeedback("", "", title, date)
        val register_request : Call<CreateFeedback> = apiService.CreateFeedback(createFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {

            override fun onResponse(call: Call<CreateFeedback>, response: Response<CreateFeedback>) {
                if (response.isSuccessful) {
                } else {
                    val StatusCode = response.code()
                }
            }
            override fun onFailure(call: Call<CreateFeedback>, t: Throwable) {
            }
        })


    }

    override fun removeItems(position: Int, id: Int, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateItems(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}