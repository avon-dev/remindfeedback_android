package com.example.remindfeedback.CategorySetting

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.remindfeedback.FeedbackList.ModelFeedback
import com.example.remindfeedback.FriendsList.ModelFriendsList
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PresenterCategorySetting: ContextCategorySetting.Presenter {
    override lateinit var view: ContextCategorySetting.View
    override lateinit var context: Context

    override fun loadItems(adapterCategorySetting: AdapterCategorySetting, list: ArrayList<ModelCategorySetting>) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetCategory> = apiService.GetCategory()
        register_request.enqueue(object : Callback<GetCategory> {
            override fun onResponse(call: Call<GetCategory>, response: Response<GetCategory>) {
                if (response.isSuccessful) {
                    val category: GetCategory = response.body()!!
                    val aaaa = category.data
                    Log.e("asdㅁㄴㅇㅁㄴㅇ", aaaa.toString())
                    if (aaaa != null) {
                        for (i in 0 until aaaa.size) {

                            var myList: myCategory_List = myCategory_List()
                            myList = aaaa[i]
                            var addData: ModelCategorySetting =
                                ModelCategorySetting(myList.category_color, myList.category_title)
                            Log.e("category_title", myList.category_title )
                            adapterCategorySetting.addItem(addData)
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

    override fun addItems(title:String, mAdapter:AdapterCategorySetting) {

        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var createCategory:CreateCategory = CreateCategory(title, "#000000")
        val register_request: Call<GetCategory> = apiService.CreateCategory(createCategory)
        register_request.enqueue(object : Callback<GetCategory> {

            override fun onResponse(call: Call<GetCategory>, response: Response<GetCategory>) {
                if (response.isSuccessful) {
                    var modelCategorySetting:ModelCategorySetting = ModelCategorySetting("#000000",title )
                    mAdapter.addItem(modelCategorySetting)
                    view.refresh()
                } else {
                }
            }

            override fun onFailure(call: Call<GetCategory>, t: Throwable) {
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