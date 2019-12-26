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

    //스피너에 보여줄 데이터 얻어옴
    override fun getCategoryData(colorArray: ArrayList<String>, titleArray: ArrayList<String>, idArray:ArrayList<Int>) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetCategory> = apiService.GetCategory()
        register_request.enqueue(object : Callback<GetCategory> {
            override fun onResponse(call: Call<GetCategory>, response: Response<GetCategory>) {
                if (response.isSuccessful) {
                    val category: GetCategory = response.body()!!
                    val mCategory = category.data
                    if (mCategory != null) {
                        for (i in 0 until mCategory.size) {
                            var myList: myCategory_List = myCategory_List()
                            myList = mCategory[i]
                            colorArray.add(myList.category_color)
                            titleArray.add(myList.category_title)
                            idArray.add(myList.category_id)
                        }
                    } else {
                    }
                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }
            }

            override fun onFailure(call: Call<GetCategory>, t: Throwable) {
            }
        })
    }
}