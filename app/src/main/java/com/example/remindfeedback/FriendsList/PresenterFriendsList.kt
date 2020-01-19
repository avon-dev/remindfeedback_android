package com.example.remindfeedback.FriendsList

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import com.example.remindfeedback.FeedbackList.ContractMain
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.GetCategory
import com.example.remindfeedback.ServerModel.GetFriends
import com.example.remindfeedback.ServerModel.myCategory_List
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

class PresenterFriendsList: ContractFriendsList.Presenter {


    lateinit override var view: ContractFriendsList.View
    lateinit override var context:Context

    override fun loadItems(list: ArrayList<ModelFriendsList>) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetFriends> = apiService.GetFriends()
        register_request.enqueue(object : Callback<GetFriends> {
            override fun onResponse(call: Call<GetFriends>, response: Response<GetFriends>) {
                if (response.isSuccessful) {
                    /*
                    val category: GetFriends = response.body()!!
                    val mCategory = category.data
                    Log.e("asdㅁㄴㅇㅁㄴㅇ", mCategory.toString())
                    if (mCategory != null) {
                        for (i in 0 until mCategory.size) {

                            var myList: myCategory_List = myCategory_List()
                            myList = mCategory[i]
                            var addData: ModelCategorySetting =
                                ModelCategorySetting(myList.category_id,myList.category_color, myList.category_title)
                            Log.e("주제", "category_id: " + myList.category_id + ", category_title: " + myList.category_title )
                            adapterCategorySetting.addItem(addData)
                            view.refresh()
                        }
                    } else {
                    }
                    */
                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }

            }

            override fun onFailure(call: Call<GetFriends>, t: Throwable) {
            }
        })
    }


    override fun addItems(email:String, adapterFriendsList: AdapterFriendsList) {
        val assetManager:AssetManager = context.resources.assets
        val inputStream= assetManager.open("eFriendsList.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("friends")

        for (i in 0 until jArray.length()) {
            val obj = jArray.getJSONObject(i)
            if(obj.getString("friendsEmail").equals(email)){
                var modelFriendsList:ModelFriendsList = ModelFriendsList(obj.getString("friendsName"),obj.getString("friendsScript"),obj.getString("friendsProfileImage"))
                adapterFriendsList.addItem(modelFriendsList)
            }
        }
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }



}