package com.avon.remindfeedback.FeedbackList.CreateFeedback.PickCategory

import android.content.Context
import android.util.Log
import com.avon.remindfeedback.Network.RetrofitFactory
import com.avon.remindfeedback.ServerModel.GetCategory
import com.avon.remindfeedback.ServerModel.myCategory_List
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PresenterPickCategory : ContractPickCategory.Presenter {


    override lateinit var view: ContractPickCategory.View
    override lateinit var context: Context

    override fun returnData(modelPickCategory: ModelPickCategory) {
        view.returnData(modelPickCategory)
    }

    override fun getData(arrayData: ArrayList<ModelPickCategory>) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
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
                            var pickCategory: ModelPickCategory = ModelPickCategory(
                                myList.category_id,
                                myList.category_color,
                                myList.category_title
                            )
                            arrayData.add(pickCategory)
                            view.refresh()
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