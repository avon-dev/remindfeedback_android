package com.avon.remindfeedback.FriendsList.FindFriends

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.avon.remindfeedback.Network.RetrofitFactory
import com.avon.remindfeedback.R
import com.avon.remindfeedback.ServerModel.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterFindFriends:ContractFindFriends.Presenter {

    override lateinit var view: ContractFindFriends.View
    override  lateinit var mContext: Context

    override fun searchFriend(email:String){

        Log.e("asd", email)
        if(email.equals("")){
            Toast.makeText(mContext, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show()
        }else{
            val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
            val apiService = RetrofitFactory.serviceAPI(client)
            var searchEmailModel: SearchEmailModel = SearchEmailModel(email)
            val register_request: Call<Object> = apiService.SearchFriends(searchEmailModel)
            register_request.enqueue(object : Callback<Object> {

                override fun onResponse(call: Call<Object>, response: Response<Object>) {
                    var jObject: JSONObject = JSONObject(Gson().toJson(response.body()))

                    if (response.isSuccessful) {
                        if(jObject.getString("data").equals("NONE")){

                        }else{
                            var mData = jObject.getJSONObject("data")


                            if(mData != null) {
                                var dialogInterface: DialogInterface? = null
                                val dialog = AlertDialog.Builder(mContext)
                                val edialog : LayoutInflater = LayoutInflater.from(mContext)
                                val mView : View = edialog.inflate(R.layout.dialog_find_friend,null)
                                dialog.setView(mView)
                                val dialog_Find_Friend_Add_Button : TextView = mView.findViewById(R.id.dialog_Find_Friend_Add_Button)
                                val dialog_Find_Friend_ImageView : ImageView = mView.findViewById(R.id.dialog_Find_Friend_ImageView)
                                val dialog_Find_Friend_Nickname_Tv : TextView = mView.findViewById(R.id.dialog_Find_Friend_Nickname_Tv)
                                val dialog_Find_Friend_Introduction_Tv : TextView = mView.findViewById(R.id.dialog_Find_Friend_Introduction_Tv)
                                dialog_Find_Friend_Nickname_Tv.text = mData.getString("nickname")
                                dialog_Find_Friend_Introduction_Tv.text = mData.getString("introduction")
                                if(!mData.getString("portrait").equals("")){
                                    Picasso.get().load("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+mData.getString("portrait")).into(dialog_Find_Friend_ImageView)
                                }else{
                                    dialog_Find_Friend_ImageView.setImageResource(R.drawable.ic_default_profile)
                                }
                                dialog_Find_Friend_Add_Button.setOnClickListener{
                                    when(mData.getInt("type")){
                                        -1 -> {//아직 친구요청을 하지 않은 사용자일때
                                            requestAddFriend(mData.getString("user_uid"))
                                            Toast.makeText(mContext, "친구신청을 완료했습니다.", Toast.LENGTH_LONG).show()
                                        }
                                        0 -> {//A가 B에게 친구요청을 했으나 B가 거절한 경우

                                        }
                                        1 -> {//A가 B에게 친구요청을 보낸 경우
                                            Toast.makeText(mContext, "이미 친구신청을 보낸 유저입니다.", Toast.LENGTH_LONG).show()
                                        }
                                        2 -> {//A와 B가 친구인 경우
                                            Toast.makeText(mContext, "이미 친구등록이 된 유저입니다..", Toast.LENGTH_LONG).show()
                                        }
                                        3 -> {//A가 B를 차단한 경우

                                        }
                                        4 -> {//B가 A를 차단한 경우

                                        }
                                        5 -> {//A와 B가 서로를 차단한 경우

                                        }
                                    }
                                    dialogInterface!!.dismiss()
                                }
                                dialog.setView(mView)
                                dialog.create()
                                dialogInterface = dialog.show()
                            }else {
                            }
                        }

                        Toast.makeText(mContext, jObject.getString("message"), Toast.LENGTH_LONG).show()

                    } else {
                        val StatusCode = response.code()
                    }
                }
                override fun onFailure(call: Call<Object>, t: Throwable) {

                }
            })
        }
    }


    override fun requestAddFriend(friend_uid:String){
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        var createFriend = CreateFriend(friend_uid)
        val register_request: Call<SearchFriend> = apiService.CreateFriend(createFriend)
        register_request.enqueue(object : Callback<SearchFriend> {
            override fun onResponse(call: Call<SearchFriend>, response: Response<SearchFriend>) {
                if (response.isSuccessful) {
                } else {
                    val StatusCode = response.code()
                }
            }
            override fun onFailure(call: Call<SearchFriend>, t: Throwable) {
            }
        })
    }
}