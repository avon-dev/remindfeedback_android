package com.example.remindfeedback.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.remindfeedback.FeedbackList.MainActivity
import com.example.remindfeedback.FriendsList.PresenterFriendsList
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), ContractRegister.View {


    private val TAG = "RegisterActivity"
    internal lateinit var presenterRegister: PresenterRegister


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("회원가입")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        presenterRegister = PresenterRegister().apply {
            view = this@RegisterActivity
        }

        //회원가입버튼을 눌러서 presenter의 회원가입 기능을 실행시킴
        register_Button.setOnClickListener {

            if(!password_Input.text.toString().equals(re_Password_Input.text.toString())){
                Toast.makeText(this@RegisterActivity, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }else{
                if(email_Input.text.toString().equals("") || nickname_Input.text.toString().equals("")  || password_Input.text.toString().equals("")  || re_Password_Input.text.toString().equals("") ){
                    Toast.makeText(this@RegisterActivity, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    presenterRegister.signup(email_Input.text.toString(), nickname_Input.text.toString(), password_Input.text.toString())
                    finish()
                }
            }

        }

    }
}
