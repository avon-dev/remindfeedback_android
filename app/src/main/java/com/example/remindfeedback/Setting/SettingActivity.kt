package com.example.remindfeedback.Setting

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.remindfeedback.FriendsList.FindFriends.FindFriendsActivity
import com.example.remindfeedback.FriendsList.FriendsListActivity
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), ContractSetting.View {
    internal lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        preferences = getSharedPreferences("USERSIGN", 0)
        val editor = preferences.edit()

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("환경설정")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        blocked_Friends.setOnClickListener(){
            val intent = Intent(this, FriendsListActivity::class.java)
            intent.putExtra("blocked", true)
            startActivity(intent)
        }
        if(preferences.getInt("sort", -1) == 1) {
            comment_Type.setText("내림차순")
        }else if(preferences.getInt("sort", -1) == 0){
            comment_Type.setText("오름차순")
        }else{
            comment_Type.setText("내림차순")
            editor.putInt("sort", 1)
            editor.commit()
        }



        comment_Type.setOnClickListener(){
            Log.e("asd", preferences.getInt("sort", -1).toString())
            if(preferences.getInt("sort", -1) == 0){
                editor.putInt("sort", 1)
                comment_Type.setText("내림차순")
            }else if(preferences.getInt("sort", -1) == 1){
                editor.putInt("sort", 0)
                comment_Type.setText("오름차순")
            }
            editor.commit()
            Toast.makeText(this, "댓글 정렬방식을 변경했습니다.", Toast.LENGTH_LONG).show()
        }
    }
}
