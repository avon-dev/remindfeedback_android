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


    override fun loadItems(list: ArrayList<ModelFeedback>, adapterMainFeedback: AdapterMainFeedback) {

        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetAllFeedback> = apiService.GetAllFeedback(0)
        register_request.enqueue(object : Callback<GetAllFeedback> {

            override fun onResponse(call: Call<GetAllFeedback>, response: Response<GetAllFeedback>) {
                if (response.isSuccessful) {
                    val testItem: GetAllFeedback = response.body()!!
                    val aaaa:getAllData? = testItem.data
                    val bbbb= aaaa!!.myFeedback
                    val cccc= aaaa!!.category
                    var tag_Color:String? = null
                    if (aaaa != null) {
                        if (bbbb != null) {
                            for (i in 0 until bbbb.size) {
                                var mfl: myFeedback = myFeedback()
                                mfl = bbbb[i]
                                val date =
                                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(mfl.write_date)
                                val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                                val dateNewFormat = sdf.format(date)
                                if (cccc != null) {
                                    for(j in 0 until cccc.size){
                                        var category_list : category = category()
                                        category_list = cccc[j]
                                        if(category_list.category_id == mfl.category){
                                            tag_Color = category_list.category_color
                                            break
                                        }
                                        else{
                                            tag_Color = "#000000"
                                        }
                                    }
                                }
                                if(tag_Color ==null){
                                }else{
                                    val addData: ModelFeedback = ModelFeedback(mfl.id, "조언자", mfl.category,tag_Color, mfl.title, "dummy", dateNewFormat, false)
                                    adapterMainFeedback.addItem(addData)
                                }

                                view.refresh()
                            }
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


        /*
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
                            val date =
                                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(mfl.write_date)
                            val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
                            val dateNewFormat = sdf.format(date)
                            var addData: ModelFeedback =
                                ModelFeedback(
                                    mfl.id,
                                    "조언자",
                                    mfl.category,
                                    mfl.title,
                                    "dummy",
                                    dateNewFormat,
                                    false
                                )
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
*/
    }

    override fun addItems(date: String?, title: String, adapterMainFeedback: AdapterMainFeedback) {
        val date2 = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
        val dateNewFormat = sdf.format(date2)
        val ndate: Date = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val createFeedback: CreateFeedback = CreateFeedback("aaaa", 3, title, ndate)
        val register_request: Call<CreateFeedback> = apiService.CreateFeedback(createFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {

            override fun onResponse(call: Call<CreateFeedback>, response: Response<CreateFeedback>) {
                if (response.isSuccessful) {
                    val addData: ModelFeedback =
                        ModelFeedback(-1, "조언자", 1,"#000000", title, "dummy", dateNewFormat, false)
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

    override fun setTagColor(category_id: Int, tagColor: TextView) {

        /*
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetCategory> = apiService.GetCategory()
        register_request.enqueue(object : Callback<GetCategory> {
            override fun onResponse(call: Call<GetCategory>, response: Response<GetCategory>) {
                if (response.isSuccessful) {
                    val category: GetCategory = response.body()!!
                    val aaaa = category.data
                    if (aaaa != null) {
                        for (i in 0 until aaaa.size) {
                            var myList: myCategory_List = myCategory_List()
                            myList = aaaa[i]
                           if(myList.category_id == category_id){
                               tagColor.setBackgroundColor(Color.parseColor((myList.category_color)))
                           }
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<GetCategory>, t: Throwable) {
            }
        })
        */
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
    override fun updateItems(id: Int, date: String?, title: String) {
        val ndate: Date = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val modifyFeedback: CreateFeedback = CreateFeedback("얍", 1, title, ndate)
        val register_request: Call<CreateFeedback> = apiService.ModifyFeedback(id, modifyFeedback)
        register_request.enqueue(object : Callback<CreateFeedback> {

            override fun onResponse(call: Call<CreateFeedback>, response: Response<CreateFeedback>) {
                if (response.isSuccessful) {
                    Log.e("피드백 수정 성공", "응")
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
    override fun modifyFeedbackActivity(id: Int, date: String?, title: String) {
        view.modifyFeedbackActivity(id, date, title)
        Log.e("Presenter", id.toString())
    }


}