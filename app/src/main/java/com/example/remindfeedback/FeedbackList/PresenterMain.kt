package com.example.remindfeedback.FeedbackList

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.*
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


    override fun loadItems(list: ArrayList<ModelFeedback?>, adapterMainFeedback: AdapterMainFeedback, feedback_count:Int) {

        var feedback_lastid:Int = 0
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllFeedback> = apiService.GetAllFeedback(feedback_count, 10)
        register_request.enqueue(object : Callback<GetAllFeedback> {

            override fun onResponse(call: Call<GetAllFeedback>, response: Response<GetAllFeedback>) {
                if (response.isSuccessful) {
                    val testItem: GetAllFeedback = response.body()!!
                    val allData:getAllData? = testItem.data
                    val mFeedback= allData!!.myFeedback
                    val mCategory= allData!!.category
                    var tag_Color:String? = null
                    if (allData != null) {
                        if (mFeedback != null) {
                            for (i in 0 until mFeedback.size) {
                                var mfl: myFeedback = myFeedback()
                                mfl = mFeedback[i]
                                val date =
                                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(mfl.write_date)
                                val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                                val dateNewFormat = sdf.format(date)
                                if (mCategory != null) {
                                    for(j in 0 until mCategory.size){
                                        var category_list : category = category()
                                        category_list = mCategory[j]
                                        if(category_list.category_id == mfl.category){
                                            //카테고리가 내 카테고리 목록에 존재하면 그걸로 표시하고 포문 끝냄
                                            tag_Color = category_list.category_color
                                            break
                                        }
                                        else{
                                            //카테고리 목록에 없을시 검정색으로 표시
                                            tag_Color = "#000000"
                                        }
                                    }
                                }
                                if(tag_Color ==null){
                                }else{
                                    val addData: ModelFeedback = ModelFeedback(mfl.id, "조언자", mfl.category,tag_Color, mfl.title, "dummy", dateNewFormat, false)
                                    adapterMainFeedback.addItem(addData)
                                }
                                feedback_lastid = mfl.id
                            }
                            view.setFeedbackCount(feedback_lastid)
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                }
                Log.e("tag", "response=" + response.raw())
            }

            override fun onFailure(call: Call<GetAllFeedback>, t: Throwable) {
            }
        })
    }




    override fun addItems(list: ArrayList<ModelFeedback?>,category_id:Int, date: String?, title: String,color:String, adapterMainFeedback: AdapterMainFeedback) {
        val date2 = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
        val dateNewFormat = sdf.format(date2)
        val ndate: Date = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val createFeedback: CreateFeedback = CreateFeedback("aaaa", category_id, title, ndate)
        val register_request: Call<CreateFeedback> = apiService.CreateFeedback(createFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {

            override fun onResponse(call: Call<CreateFeedback>, response: Response<CreateFeedback>) {
                if (response.isSuccessful) {
                    val addData: ModelFeedback =
                        ModelFeedback(-1, "조언자", category_id,color, title, "dummy", dateNewFormat, false)
                    adapterMainFeedback.addItem(addData)
                    list.clear()
                    loadItems(list, adapterMainFeedback,0)
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

    // 피드백 수정
    override fun updateItems(list: ArrayList<ModelFeedback?>,item_id:Int,category_id: Int, date: String?, title: String,color:String, adapterMainFeedback: AdapterMainFeedback) {
        val ndate: Date = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val modifyFeedback: CreateFeedback = CreateFeedback("얍", category_id, title, ndate)
        val register_request: Call<CreateFeedback> = apiService.ModifyFeedback(item_id, modifyFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {

            override fun onResponse(call: Call<CreateFeedback>, response: Response<CreateFeedback>) {
                if (response.isSuccessful) {
                    list.clear()
                    loadItems(list, adapterMainFeedback,0)
                    view.refresh()
                } else {
                }
            }
            override fun onFailure(call: Call<CreateFeedback>, t: Throwable) {
                Log.e("피드백 수정 실패", t.message)
            }

        })
    }

    // 피드백 수정화면 띄우기
    override fun modifyFeedbackActivity(id: Int,category_id: Int, date: String?, title: String) {
        view.modifyFeedbackActivity(id, category_id,date, title)
    }



}