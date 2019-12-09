package com.example.remindfeedback.CategorySetting

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.remindfeedback.FeedbackList.ModelFeedback
import com.example.remindfeedback.FriendsList.ModelFriendsList
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.Category
import com.example.remindfeedback.ServerModel.CreateCategory
import com.example.remindfeedback.ServerModel.TestItem
import com.example.remindfeedback.ServerModel.myFeedback_List
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
        val register_request: Call<Category> = apiService.GetCategory()
        register_request.enqueue(object : Callback<Category> {

            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                if (response.isSuccessful) {
                    val category: Category = response.body()!!
                    val aaaa = category.data
                    Log.e("asdㅁㄴㅇㅁㄴㅇ", aaaa)
                    /*
                    if (aaaa != null) {
                        for (i in 0 until aaaa.size) {
                            var mfl: myFeedback_List = myFeedback_List()
                            mfl = aaaa[i]
                            var addData: Category =
                                Category()
                            adapterCategorySetting.addItem(addData)
                            view.refresh()
                        }
                    } else {
                    }

                    */
                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }
                Log.e("tag", "response=" + response.raw())
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
            }
        })
    }

    override fun addItems(title:String, mAdapter:AdapterCategorySetting) {

        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var createCategory:CreateCategory = CreateCategory(title, "#ffffff")
        val register_request: Call<Category> = apiService.CreateCategory(createCategory)
        register_request.enqueue(object : Callback<Category> {

            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                if (response.isSuccessful) {
                    var modelCategorySetting:ModelCategorySetting = ModelCategorySetting("",title )
                    mAdapter.addItem(modelCategorySetting)
                    view.refresh()
                } else {
                }
                Log.e("tag", "response=" + response.raw())
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
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