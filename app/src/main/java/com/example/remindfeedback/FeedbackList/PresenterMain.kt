package com.example.remindfeedback.FeedbackList

import android.content.Context
import android.util.Log
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.CreateFeedback
import com.example.remindfeedback.ServerModel.GetFeedback
import com.example.remindfeedback.ServerModel.myFeedback_List
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PresenterMain : ContractMain.Presenter {

    lateinit override var view: ContractMain.View
    lateinit override var context: Context


    override fun loadItems(list: ArrayList<ModelFeedback>, adapterMainFeedback: AdapterMainFeedback) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetFeedback> = apiService.GetFeedback(0)
        register_request.enqueue(object : Callback<GetFeedback> {

            override fun onResponse(call: Call<GetFeedback>, response: Response<GetFeedback>) {
                if (response.isSuccessful) {
                    val testItem: GetFeedback = response.body()!!
                    val aaaa = testItem.data
                    if (aaaa != null) {
                        for (i in 0 until aaaa.size) {
                            var mfl: myFeedback_List = myFeedback_List()
                            mfl = aaaa[i]
                            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(mfl.write_date)
                            val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                            val dateNewFormat = sdf.format(date)
                            var addData: ModelFeedback =
                                ModelFeedback(mfl.id, "조언자", mfl.category, mfl.title, "dummy", dateNewFormat, false)
                            adapterMainFeedback.addItem(addData)
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                }
                Log.e("tag", "response=" + response.raw())
            }

            override fun onFailure(call: Call<GetFeedback>, t: Throwable) {
            }
        })

    }

    override fun addItems(date: String?, title: String, adapterMainFeedback: AdapterMainFeedback) {
        val date2 = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
        val dateNewFormat = sdf.format(date2)
        val ndate:Date = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val createFeedback: CreateFeedback = CreateFeedback("aaaa", 1, title, ndate)
        val register_request: Call<CreateFeedback> = apiService.CreateFeedback(createFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {

            override fun onResponse(call: Call<CreateFeedback>, response: Response<CreateFeedback>) {
                if (response.isSuccessful) {
                    val addData: ModelFeedback =
                        ModelFeedback(-1, "조언자", 1, title, "dummy", dateNewFormat, false)
                    adapterMainFeedback.addItem(addData)
                    view.refresh()
                } else {
                    val StatusCode = response.code()
                }
            }

            override fun onFailure(call: Call<CreateFeedback>, t: Throwable) {
            }
        })


    }

    override fun removeItems(id: Int, context: Context) {
        Log.e("리무브 테스트", "$id")
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.DeleteFeedback(id)
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.e("성공!", "딜리트 성공")
                    view.refresh()
                } else {
                    val StatusCode = response.code()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("실패", t.message)

            }
        })
    }

    override fun updateItems(id: Int) {
    }

}