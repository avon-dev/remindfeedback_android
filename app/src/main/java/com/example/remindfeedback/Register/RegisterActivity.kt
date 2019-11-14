package com.example.remindfeedback.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.remindfeedback.R

class RegisterActivity : AppCompatActivity(), ContractRegister.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("회원가입")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)
    }
}
