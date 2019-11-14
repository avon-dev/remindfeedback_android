package com.example.remindfeedback.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.remindfeedback.R

class SettingActivity : AppCompatActivity(),ContractSetting.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("환경설정")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)
    }
}
