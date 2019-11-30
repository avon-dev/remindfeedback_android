package com.example.remindfeedback.FriendsList.FindFriends

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_find_friends.*

class FindFriendsActivity : AppCompatActivity(),ContractFindFriends.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_friends)

        //액션바 설정
        var ab:ActionBar = this!!.supportActionBar!!
        ab.setTitle("친구 추가")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)
    }

    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.find_friends_menu, menu)
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){
            R.id.friends_Find_Button -> { return friends_Find_Button() }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }
    //찾기버튼 눌렀을때
    fun friends_Find_Button(): Boolean {
        Toast.makeText(this@FindFriendsActivity, "찾기 누름.", Toast.LENGTH_SHORT).show()
        val intent = Intent()
        intent.putExtra("email", find_Friends_Email.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
        return true
    }



}
