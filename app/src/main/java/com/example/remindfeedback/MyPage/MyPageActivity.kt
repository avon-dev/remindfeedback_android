package com.example.remindfeedback.MyPage

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.example.remindfeedback.Login.PresenterLogin
import com.example.remindfeedback.MyPage.ImagePick.ImagePickActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_my_page.*
import java.net.URL
import java.security.AccessController.getContext
import android.R.attr.bitmap
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import android.R.attr.bitmap
import android.graphics.Bitmap
import com.example.remindfeedback.etcProcess.BasicDialog
import com.example.remindfeedback.etcProcess.URLtoBitmapTask


class MyPageActivity : AppCompatActivity() , ContractMyPage.View{

    private val TAG = "MyPageActivity"
    internal lateinit var presenterMyPage: PresenterMyPage
    var imageData:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("마이 페이지")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)


        presenterMyPage = PresenterMyPage().apply {
            view = this@MyPageActivity
            mContext = this@MyPageActivity
        }
        presenterMyPage.getInfo()

        //다이알로그에 사용할 params
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.leftMargin = resources.getDimensionPixelSize(R.dimen.dialog_margin)
        params.rightMargin = resources.getDimensionPixelSize(R.dimen.dialog_margin)

        //닉네임 수정 버튼 눌렀을때
        patch_My_Nickname_Button.setOnClickListener {
            presenterMyPage.showDialog("닉네임",this, params)

        }
        //상태메세지 수정 버튼 눌렀을때
        patch_My_Introduction_Button.setOnClickListener {
            presenterMyPage.showDialog("상태메세지",this, params)

        }
        //프로필 이미지 수정 버튼 눌렀을때
        patch_My_Portrait_Button.setOnClickListener {
            val intent = Intent(this, ImagePickActivity::class.java)
            intent.putExtra("imageData", imageData)
            Log.e("aaaaa", imageData)
            startActivityForResult(intent, 100)
        }
        logout_Button.setOnClickListener{
            var basicDialog: BasicDialog = BasicDialog("로그아웃 하시겠습니까?", this, { presenterMyPage.logout()
                finish()}, {})
            basicDialog.makeDialog()
        }
        delete_Account_Button.setOnClickListener{
            var basicDialog: BasicDialog = BasicDialog("회원탈퇴 하시겠습니까?", this, { presenterMyPage.deleteAccount()
                finish()}, {})
            basicDialog.makeDialog()
        }
    }

    //presenter에서 얻어온 데이터로 화면에 데이터 반영
    override fun setInfo(email: String, nickname: String, portrait: String?, introduction: String?) {
        mypage_Nickname_Tv.text = nickname
        mypage_Email_Tv.text = email
//        mypage_Email_Tv_2.text = email
        mypage_Introduction_Tv.text = introduction
        if(!portrait.equals("")){
            //이미지 설정해주는 부분
            var test_task: URLtoBitmapTask = URLtoBitmapTask()
            test_task = URLtoBitmapTask().apply {
                url = URL("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+portrait)
                imageData = "https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+portrait
            }
            var bitmap:Bitmap = test_task.execute().get()
            profile_ImageView.setImageBitmap(bitmap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            100 -> {
                when(resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        //presenterMain.addItems(data.getStringExtra("date"),data.getStringExtra("title"),mAdapter)
                        presenterMyPage.patchPortrait(data.getStringExtra("fileUri"))
                    }
                    Activity.RESULT_CANCELED -> Toast.makeText(this@MyPageActivity, "취소됨.", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}
