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
import com.example.remindfeedback.ServerModel.CreateFeedback
import com.example.remindfeedback.ServerModel.SearchEmailModel
import com.example.remindfeedback.ServerModel.SearchFriend
import com.example.remindfeedback.ServerModel.getFriendInfo
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
                            Log.e("asdd", "프로필 이미지 "+fData.portrait)

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
                                Log.e("fData", fData.email + fData.introduction + fData.nickname + fData.portrait+ fData.type + fData.user_uid)
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
}