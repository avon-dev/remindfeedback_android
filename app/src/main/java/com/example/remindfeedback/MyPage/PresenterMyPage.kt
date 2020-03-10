package com.example.remindfeedback.MyPage

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.remindfeedback.Login.FindPassword.FindPasswordActivity
import com.example.remindfeedback.Login.LoginActivity
import com.example.remindfeedback.Network.RetrofitFactory
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.CheckingPassword
import com.example.remindfeedback.ServerModel.GetMyPage
import com.example.remindfeedback.ServerModel.GetSuccessData
import com.example.remindfeedback.ServerModel.RequestFindPassword
import com.example.remindfeedback.etcProcess.Sha256Util
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.security.KeyStore


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
        val requestBody2 = RequestBody.create(MediaType.parse("multipart/data"), "true")

        //이미지 사이즈
        val lFileSize: Long = image_File.length()
        Log.e("lFileSize", lFileSize.toString())
        if(lFileSize < 200000){//이미지의 크기가 20메가 이하일때는 그냥 진행
            val multiPartBody = MultipartBody.Part
                .createFormData("portrait", image_File.name, requestBody)

            val request : Call<GetMyPage> = apiService.PatchPortrait(multiPartBody,requestBody2)
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
        }else{//이미지크기가 20메가 넘어가면 안됨
            val options = BitmapFactory.Options()
            options.inSampleSize = ((lFileSize/200000)+1).toInt()
            val src = BitmapFactory.decodeFile(fileUri,options)
            val resized = Bitmap.createScaledBitmap(src, 500, 500, true)
            var newFile = File(fileUri)
            var out:OutputStream? = null
            newFile.createNewFile()
            out = FileOutputStream(newFile)
            resized.compress(Bitmap.CompressFormat.PNG, 100, out)
            Log.e("lFileSize2",File(fileUri).length().toString())
            //이미지 크기 맞춰질때까지 리사이즈
            patchPortrait(fileUri)
        }

    }
    override fun logout() {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.LogOut()
        register_request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.e("로그아웃", "성공")
                    view.appReset()
                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    //비밀번호를 다시입력하고 탈퇴를 해야할거같은데 그럴수 있는 방법이 없는거같음 이거는 일단 보류
    override fun inputPassword(type:String,mEmail:String) {
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.leftMargin = mContext.resources.getDimensionPixelSize(R.dimen.dialog_margin)
        params.rightMargin = mContext.resources.getDimensionPixelSize(R.dimen.dialog_margin)
        val container = FrameLayout(mContext)
        val et = EditText(mContext)
        //et.setInputType(InputType.TYPE_CLASS_TEXT )
        //et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
        et.setLayoutParams(params)
        et.setSingleLine(true)

        val FilterArray = arrayOfNulls<InputFilter>(1)
        FilterArray[0] = InputFilter.LengthFilter(15)
        et.setFilters(FilterArray)

        container.addView(et)
        val alt_bld = AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle)
        alt_bld.setTitle("비밀번호를 입력 해주세요")
            //.setMessage("비밀번호를 입력 해주세요")
            .setCancelable(true)
            .setView(container)
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                val value = Sha256Util.testSHA256(et.getText().toString())
                checkPassword(value,type,mEmail)

            })
        val alert = alt_bld.create()
        alert.show()



    }
    override fun checkPassword(password:String, type:String,mEmail:String){
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<GetSuccessData> = apiService.CheckPassword(CheckingPassword(password))
        register_request.enqueue(object : Callback<GetSuccessData> {
            override fun onResponse(call: Call<GetSuccessData>, response: Response<GetSuccessData>) {
                if (response.isSuccessful) {
                    var mData:GetSuccessData = response.body()!!
                    Toast.makeText(mContext, mData.message, Toast.LENGTH_LONG).show()
                    if(mData.success){
                        if(type.equals("delete")){
                            deleteAccount()
                        }else if(type.equals("change")){
                            findPassword(mEmail)
                        }

                    }
                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }
            }
            override fun onFailure(call: Call<GetSuccessData>, t: Throwable) {
            }
        })
    }

    override fun findPassword(email: String) {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)

        val register_request: Call<ResponseBody> = apiService.RequestFindPassword(
            RequestFindPassword(email)
        )
        register_request.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(mContext, "인증토큰 전송이 완료되었습니다.", Toast.LENGTH_LONG).show()
                    val intent = Intent(mContext, FindPasswordActivity::class.java)
                    intent.putExtra("isChange", true)
                    mContext.startActivity(intent)
                } else {
                    Toast.makeText(mContext, "에러가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }


    override fun deleteAccount() {
        val client: OkHttpClient = RetrofitFactory.getClient(mContext, "addCookie")
        val apiService = RetrofitFactory.serviceAPI(client)
        val register_request: Call<ResponseBody> = apiService.DeleteAccount()
        register_request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.e("회원탈퇴", "성공")
                    view.appReset()
                    Toast.makeText(mContext, "성공적으로 회원탈퇴 되었습니다.", Toast.LENGTH_LONG).show()
                } else {
                    Log.e("asdasdasd", "뭔가 실패함")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }
}