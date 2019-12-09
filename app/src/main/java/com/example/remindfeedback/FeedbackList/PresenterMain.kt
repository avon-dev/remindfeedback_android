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
                    Log.e("testitem", aaaa.toString())
                    if (aaaa != null) {
                        for (i in 0 until aaaa.size) {
                            var mfl: myFeedback_List = myFeedback_List()
                            mfl = aaaa[i]
                            var addData: ModelFeedback =
                                ModelFeedback(mfl.id, "조언자", mfl.category, mfl.title, "dummy", mfl.write_date, false)
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

    override fun addItems(id: Int, title: String, adapterMainFeedback: AdapterMainFeedback) {

        // 현재시간을 msec 으로 구한다.
        val now = System.currentTimeMillis()
        // 현재시간을 date 변수에 저장한다.
        val date = Date(now)
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var createFeedback: CreateFeedback = CreateFeedback("aaaa", 1, title, date)
        val register_request: Call<CreateFeedback> = apiService.CreateFeedback(createFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {

            override fun onResponse(call: Call<CreateFeedback>, response: Response<CreateFeedback>) {
                if (response.isSuccessful) {
                    var addData: ModelFeedback =
                        ModelFeedback(id, "조언자", 1, title, "dummy", date.toString(), false)
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