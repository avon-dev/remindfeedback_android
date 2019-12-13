package com.example.remindfeedback.MyPage

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.remindfeedback.FriendsList.ContractFriendsList
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.GetMyPage
import com.example.remindfeedback.ServerModel.LogIn
import com.example.remindfeedback.ServerModel.myPage_Data
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterMyPage : ContractMyPage.Presenter{

    lateinit override var view: ContractMyPage.View
    lateinit override var mContext: Context
    override fun getInfo() {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext,"addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val request : Call<GetMyPage> = apiService.GetMyPage()
        request.enqueue(object : Callback<GetMyPage> {
            override fun onResponse(call: Call<GetMyPage>, response: Response<GetMyPage>) {
                if (response.isSuccessful) {
                    //데이터 얻어서 activity로 보내줌
                    val getMyPage:GetMyPage = response.body()!!
                    val info  = getMyPage.data
                    view.setInfo(info!!.email!!,info.nickname, info.portrait!!, info.introduction!! )
                } else {
                }
            }
            override fun onFailure(call: Call<GetMyPage>, t: Throwable) {
            }
        })

    }

    override fun showDialog(showText: String, context: Context, params: FrameLayout.LayoutParams) {
        val container = FrameLayout(context)
        val et = EditText(context)
        et.setLayoutParams(params)
        container.addView(et)
        val alt_bld = AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
        alt_bld.setTitle(showText+" 변경")
            .setMessage("변경할 "+showText+"을 입력하세요")
            .setIcon(R.drawable.ic_add_black)
            .setCancelable(false)
            .setView(container)
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                val value = et.getText().toString()
                if(!value.equals("")){
                    if(showText.equals("닉네임")){
                        patchNickname(value)
                    }
                }else{
                    Toast.makeText(context, showText+"을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            })
        val alert = alt_bld.create()
        alert.show()
    }


    override fun patchNickname(nickname: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext,"addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val request : Call<GetMyPage> = apiService.PatchNickname(nickname)
        request.enqueue(object : Callback<GetMyPage> {
            override fun onResponse(call: Call<GetMyPage>, response: Response<GetMyPage>) {
                if (response.isSuccessful) {
                    //데이터 얻어서 activity로 보내줌
                    val getMyPage:GetMyPage = response.body()!!
                    val info  = getMyPage.data
                    view.setInfo(info!!.email!!,info.nickname, info.portrait!!, info.introduction!! )
                } else {
                }
            }
            override fun onFailure(call: Call<GetMyPage>, t: Throwable) {
            }
        })
    }
}