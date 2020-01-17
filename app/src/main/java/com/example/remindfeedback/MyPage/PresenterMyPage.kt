package com.example.remindfeedback.MyPage

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.text.InputFilter
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginTop
import com.example.remindfeedback.FriendsList.ContractFriendsList
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.GetMyPage
import com.example.remindfeedback.ServerModel.LogIn
import com.example.remindfeedback.ServerModel.myPage_Data
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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
        params.leftMargin = context.resources.getDimensionPixelSize(R.dimen.dialog_margin)
        params.rightMargin = context.resources.getDimensionPixelSize(R.dimen.dialog_margin)
        et.setLayoutParams(params)
        et.setSingleLine(true)

        val FilterArray = arrayOfNulls<InputFilter>(1)
        FilterArray[0] = InputFilter.LengthFilter(15)
        et.setFilters(FilterArray)

        container.addView(et)
        val alt_bld = AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
        alt_bld.setTitle(showText+" 변경")
            .setMessage("변경할 "+showText+"을 입력하세요")
            .setIcon(R.drawable.ic_how_to_reg_black_24dp)
            .setCancelable(true)
            .setView(container)
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                val value = et.getText().toString()
                if(!value.equals("")){
                    if(showText.equals("닉네임")){
                        patchNickname(value)
                    }else if(showText.equals("상태메세지")){
                        patchIntroduction(value)
                    }
                }else{
                    Toast.makeText(context, showText+"을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            })
        val alert = alt_bld.create()
        alert.show()

        // 메세지 텍스트 변경
        var textView = alert.findViewById<TextView>(android.R.id.message)
        textView?.setTextColor(Color.BLACK)
    }


    fun patchNickname(nickname: String) {
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

    fun patchIntroduction(introduction: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext,"addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val request : Call<GetMyPage> = apiService.PatchIntoduction(introduction)
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

    override fun patchPortrait(fileUri:String?) {
        Log.e("p_mypage", fileUri)
        val client: OkHttpClient = RetrofitFactory.getClient(mContext,"addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val image_File = File(fileUri)
        val requestBody = RequestBody.create(MediaType.parse("multipart/data"), image_File)
        val multiPartBody = MultipartBody.Part
            .createFormData("portrait", image_File.name, requestBody)

        val request : Call<GetMyPage> = apiService.PatchPortrait(multiPartBody)
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