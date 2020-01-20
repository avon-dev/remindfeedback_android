package com.example.remindfeedback.FriendsList

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import android.widget.Toast
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import com.example.remindfeedback.FeedbackList.ContractMain
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.ServerModel.*
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

    override fun loadItems(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList) {
        list.clear()
        view.refresh()
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetFriends> = apiService.GetFriends()
        register_request.enqueue(object : Callback<GetFriends> {
            override fun onResponse(call: Call<GetFriends>, response: Response<GetFriends>) {
                if (response.isSuccessful) {
                    val fData: GetFriends = response.body()!!
                    val fList = fData.data
                    if (fList != null) {
                        for (i in 0 until fList.size) {
                            var myList: getFriendsInfo = getFriendsInfo()
                            myList = fList[i]
                            var addData: ModelFriendsList = ModelFriendsList(myList.user_uid, myList.nickname!!, myList.introduction!!, myList.introduction!!, myList.type, 0)
                            adapterFriendsList.addItem(addData)
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


    override fun receivedFriendRequests(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList) {
        list.clear()
        view.refresh()
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetFriends> = apiService.GetReceivedFriendRequest()
        register_request.enqueue(object : Callback<GetFriends> {
            override fun onResponse(call: Call<GetFriends>, response: Response<GetFriends>) {
                if (response.isSuccessful) {
                    val fData: GetFriends = response.body()!!
                    val fList = fData.data
                    if (fList != null) {
                        for (i in 0 until fList.size) {
                            var myList: getFriendsInfo = getFriendsInfo()
                            myList = fList[i]
                            var addData: ModelFriendsList = ModelFriendsList(myList.user_uid, myList.nickname!!, myList.introduction!!, myList.introduction!!, myList.type, 1)
                            adapterFriendsList.addItem(addData)
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                }

            }
            override fun onFailure(call: Call<GetFriends>, t: Throwable) {
            }
        })
    }

    override fun requestedFriendsRequests(list: ArrayList<ModelFriendsList>, adapterFriendsList: AdapterFriendsList) {
        list.clear()
        view.refresh()
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetFriends> = apiService.GetRequestedFriendRequest()
        register_request.enqueue(object : Callback<GetFriends> {
            override fun onResponse(call: Call<GetFriends>, response: Response<GetFriends>) {
                if (response.isSuccessful) {
                    val fData: GetFriends = response.body()!!
                    val fList = fData.data
                    if (fList != null) {
                        for (i in 0 until fList.size) {
                            var myList: getFriendsInfo = getFriendsInfo()
                            myList = fList[i]
                            var addData: ModelFriendsList = ModelFriendsList(myList.user_uid, myList.nickname!!, myList.introduction!!, myList.introduction!!, myList.type,2)
                            adapterFriendsList.addItem(addData)
                            view.refresh()
                        }
                    } else {
                    }
                } else {
                }

            }
            override fun onFailure(call: Call<GetFriends>, t: Throwable) {
            }
        })
    }


    override fun acceptRequest(list: ArrayList<ModelFriendsList>,user_uid: String, adapterFriendsList: AdapterFriendsList) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var createFriend = CreateFriend(user_uid)
        val register_request: Call<SearchFriend> = apiService.CreateFriend(createFriend)
        register_request.enqueue(object : Callback<SearchFriend> {
            override fun onResponse(call: Call<SearchFriend>, response: Response<SearchFriend>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "친구요청이 수락되었습니다.", Toast.LENGTH_LONG).show()
                    list.clear()
                    receivedFriendRequests(list, adapterFriendsList)
                    view.refresh()
                } else {
                    val StatusCode = response.code()
                }
            }
            override fun onFailure(call: Call<SearchFriend>, t: Throwable) {
            }
        })
    }

    override fun rejectRequest(list: ArrayList<ModelFriendsList>,user_uid: String, adapterFriendsList: AdapterFriendsList) {
        val client: OkHttpClient = RetrofitFactory.getClient(context, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var createFriend = CreateFriend(user_uid)
        val register_request: Call<SearchFriend> = apiService.RejectFriend(createFriend)
        register_request.enqueue(object : Callback<SearchFriend> {
            override fun onResponse(call: Call<SearchFriend>, response: Response<SearchFriend>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "친구요청이 거절되었습니다.", Toast.LENGTH_LONG).show()
                    list.clear()
                    receivedFriendRequests(list, adapterFriendsList)
                    view.refresh()
                } else {
                    val StatusCode = response.code()
                }
            }
            override fun onFailure(call: Call<SearchFriend>, t: Throwable) {
            }
        })
    }

    override fun addItems(email:String, adapterFriendsList: AdapterFriendsList) {

    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }



}