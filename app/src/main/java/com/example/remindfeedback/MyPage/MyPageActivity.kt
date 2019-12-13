package com.example.remindfeedback.MyPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.remindfeedback.Login.PresenterLogin
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPageActivity : AppCompatActivity() , ContractMyPage.View{

    private val TAG = "MyPageActivity"
    internal lateinit var presenterMyPage: PresenterMyPage
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

    }

    //presenter에서 얻어온 데이터로 화면에 데이터 반영
    override fun setInfo(email: String, nickname: String, portrait: String?, introduction: String?) {
        mypage_Nickname_Tv.text = nickname
        mypage_Email_Tv.text = email
        mypage_Email_Tv_2.text = email
    }

}
