package com.example.remindfeedback.FeedbackList.CreateFeedback.ChoiceAdviser

import android.content.Context
import android.util.Log
import com.example.remindfeedback.FeedbackList.AdapterMainFeedback
import com.example.remindfeedback.FeedbackList.ModelFeedback
import com.example.remindfeedback.FriendsList.AdapterFriendsList
import com.example.remindfeedback.FriendsList.ContractFriendsList
import com.example.remindfeedback.FriendsList.ModelFriendsList
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.GetFriends
import com.example.remindfeedback.ServerModel.getFriendsInfo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PresenterChoiceAdviser : ContractChoiceAdviser.Presenter {


    lateinit override var view: ContractChoiceAdviser.View
    lateinit override var context: Context


    override fun loadItems(
        list: ArrayList<ModelFriendsList>,
        adapterChoiceAdviser: AdapterChoiceAdviser
    ) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetFriends> = apiService.GetAdviser()
        register_request.enqueue(object : Callback<GetFriends> {
            override fun onResponse(call: Call<GetFriends>, response: Response<GetFriends>) {
                if (response.isSuccessful) {
                    val fData: GetFriends = response.body()!!
                    val fList = fData.data
                    if (fList != null) {
                        for (i in 0 until fList.size) {
                            var myList: getFriendsInfo = getFriendsInfo()
                            myList = fList[i]
                            var addData: ModelFriendsList = ModelFriendsList(
                                myList.user_uid,
                                myList.id,
                                myList.nickname!!,
                                myList.introduction!!,
                                myList.portrait!!,
                                myList.type,
                                0
                            )
                            adapterChoiceAdviser.addItem(addData)
                            Log.e("asdasd", myList.nickname)
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }

            }

            override fun onFailure(call: Call<GetFriends>, t: Throwable) {
            }
        })
    }

    override fun returnData(user_uid: String) {
        Log.e("adapter choice", user_uid)

        view.returnData(user_uid)
    }


}