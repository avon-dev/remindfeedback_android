package com.example.remindfeedback.FeedbackList

import android.content.Context
import android.util.Log
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import com.example.remindfeedback.Network.AddCookiesInterceptor
import com.example.remindfeedback.Network.ServiceAPI
import com.example.remindfeedback.ServerModel.CreateFeedback
import com.example.remindfeedback.ServerModel.GetFeedback
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
    override fun loadItems(list: ArrayList<ModelFeedback>) {
        //로그찍는 부분
        val loggingInterceptor =  HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val addCookiesInterceptor = AddCookiesInterceptor(context)
        val client = OkHttpClient.Builder()
            .addInterceptor(addCookiesInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://54.180.118.35/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        val apiService = retrofit.create(ServiceAPI::class.java!!)
        val register_request : Call<GetFeedback> = apiService.GetFeedback()
        register_request.enqueue(object : Callback<GetFeedback> {

            override fun onResponse(call: Call<GetFeedback>, response: Response<GetFeedback>) {
                if (response.isSuccessful) {


                    Log.e("asd",response.body()!!.toString())

                    var getFeedback:GetFeedback = response.body()!!
                  var jsonArray:JSONArray = getFeedback.mArray
                    //Log.e("myfeeback",jsonObject.toString())

                    //Log.e("myfeeback", myfeeback.myFeedbackArray.length().toString())
                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }
            override fun onFailure(call: Call<GetFeedback>, t: Throwable) {

            }
        })

    }

    override fun addItems(title:String, adapterMainFeedback: AdapterMainFeedback) {
        //로그찍는 부분
        val loggingInterceptor =  HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val addCookiesInterceptor = AddCookiesInterceptor(context)
        val client = OkHttpClient.Builder()
            .addInterceptor(addCookiesInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://54.180.118.35/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()


        // 현재시간을 msec 으로 구한다.
        val now = System.currentTimeMillis()
        // 현재시간을 date 변수에 저장한다.
        val date = Date(now)

        Log.e("asda", date.toString())
        Log.e("title", title)
        val apiService = retrofit.create(ServiceAPI::class.java!!)
        var createFeedback:CreateFeedback = CreateFeedback("", "", title, date)
        val register_request : Call<CreateFeedback> = apiService.CreateFeedback(createFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {

            override fun onResponse(call: Call<CreateFeedback>, response: Response<CreateFeedback>) {
                if (response.isSuccessful) {
                    Log.e("createfeedback", "여기 createfeedback")
                    Log.e("response", " ${response.headers()}")

                } else {
                    val StatusCode = response.code()
                    Log.e("post", "Status Code : $StatusCode")
                }
                Log.e("tag", "response=" + response.raw())
            }
            override fun onFailure(call: Call<CreateFeedback>, t: Throwable) {
                    Log.e("asdaa",call.toString() )
                Log.e("asdaa",t.message)

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