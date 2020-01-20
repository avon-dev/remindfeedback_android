package com.example.remindfeedback.FriendsList.FindFriends

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.remindfeedback.FeedbackList.ModelFeedback
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.*
import com.example.remindfeedback.etcProcess.URLtoBitmapTask
import kotlinx.android.synthetic.main.activity_my_page.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

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
            val register_request: Call<SearchFriend> = apiService.SearchFriends(searchEmailModel)
            register_request.enqueue(object : Callback<SearchFriend> {

                override fun onResponse(call: Call<SearchFriend>, response: Response<SearchFriend>) {
                    if (response.isSuccessful) {
                        var fInfo: SearchFriend = response.body()!!
                        var fData = fInfo.data
                        if(fData != null) {
                            val dialog = AlertDialog.Builder(mContext)
                            val edialog : LayoutInflater = LayoutInflater.from(mContext)
                            val mView : View = edialog.inflate(R.layout.dialog_find_friend,null)
                            val dialog_Find_Friend_Add_Button : TextView = mView.findViewById(R.id.dialog_Find_Friend_Add_Button)
                            val dialog_Find_Friend_ImageView : ImageView = mView.findViewById(R.id.dialog_Find_Friend_ImageView)
                            val dialog_Find_Friend_Nickname_Tv : TextView = mView.findViewById(R.id.dialog_Find_Friend_Nickname_Tv)
                            val dialog_Find_Friend_Introduction_Tv : TextView = mView.findViewById(R.id.dialog_Find_Friend_Introduction_Tv)
                            dialog_Find_Friend_Nickname_Tv.text = fData.nickname
                            dialog_Find_Friend_Introduction_Tv.text = fData.introduction
                            if(!fData.portrait.equals("")){
                                var test_task: URLtoBitmapTask = URLtoBitmapTask()
                                test_task = URLtoBitmapTask().apply {
                                    url = URL("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fData.portrait)
                                }
                                var bitmap: Bitmap = test_task.execute().get()
                                dialog_Find_Friend_ImageView.setImageBitmap(bitmap)
                            }else{
                                dialog_Find_Friend_ImageView.setImageResource(R.drawable.ic_default_profile)
                            }
                            dialog_Find_Friend_Add_Button.setOnClickListener{
                                when(fData.type){
                                    -1 -> {//아직 친구요청을 하지 않은 사용자일때
                                        requestAddFriend(fData.user_uid)
                                        fData.type = 1
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
                            }
                            dialog.setView(mView)
                            dialog.create()
                            dialog.show()
                        }else {
                        }
                    } else {
                        val StatusCode = response.code()
                    }
                }
                override fun onFailure(call: Call<SearchFriend>, t: Throwable) {
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