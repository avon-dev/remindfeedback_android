package com.example.remindfeedback.CreateFeedback

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.remindfeedback.FeedbackList.MainActivity
import com.example.remindfeedback.FeedbackList.ModelFeedback
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_create_feedback.*

class CreateFeedbackActivity : AppCompatActivity(), ContractCreateFeedback.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_feedback)


        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("새로운 피드백")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)


    }
    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create_feedback_menu, menu)
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){
            R.id.create_Feedback_Button -> { return create_Feedback_Button() }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }
    //버튼 눌렀을때
    fun create_Feedback_Button(): Boolean {
        Toast.makeText(this@CreateFeedbackActivity, "작성 누름.", Toast.LENGTH_SHORT).show()

        MainActivity.arrayList.add(ModelFeedback("박혜련","black",create_Feedback_Title.text.toString(),"dummy","2019.12.01 일",true))


        finish()

        return true
    }

}
