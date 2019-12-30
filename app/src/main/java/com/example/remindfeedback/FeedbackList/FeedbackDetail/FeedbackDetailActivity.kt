package com.example.remindfeedback.FeedbackList.FeedbackDetail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.Alarm.AdapterAlarm
import com.example.remindfeedback.Alarm.ModelAlarm
import com.example.remindfeedback.Alarm.PresenterAlarm
import com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost.CreatePostActivity
import com.example.remindfeedback.FeedbackList.FeedbackDetail.Post.PostActivity
import com.example.remindfeedback.FriendsList.FindFriends.FindFriendsActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.CreateBoardText
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.activity_feedback_detail.*

class FeedbackDetailActivity : AppCompatActivity() , ContractFeedbackDetail.View{


    private val TAG = "FeedbackDetailActivity"
    internal lateinit var presenterFeedbackDetail: PresenterFeedbackDetail
    var arrayList = arrayListOf<ModelFeedbackDetail>(
       // ModelFeedbackDetail("사진", "목요일반 수업 발표연습", "2019년 9월 31일 오후 3시 30분", 5)
    )
    var feedback_id:Int = -1
    lateinit var mAdapter:AdapterFeedbackDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_detail)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        presenterFeedbackDetail = PresenterFeedbackDetail().apply {
            view = this@FeedbackDetailActivity
            mContext = this@FeedbackDetailActivity
        }
        mAdapter = AdapterFeedbackDetail(this, arrayList, presenterFeedbackDetail)

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        feedback_Detail_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        feedback_Detail_Recyclerview.layoutManager = lm
        feedback_Detail_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        val intent = intent
        feedback_id= intent.getIntExtra("feedback_id", -1)
        feedback_Detail_Title_Tv.text = "["+intent.getStringExtra("title")+"]에 대한 피드백"
        feedback_Detail_Date_Tv.text = "목표일 : "+intent.getStringExtra("date")

        presenterFeedbackDetail.loadItems(arrayList, mAdapter,feedback_id)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            111 -> {    // 피드백 추가 후 돌아왔을 때
                when(resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        var createBoardText = CreateBoardText(data.getIntExtra("feedback_id", -1), data.getStringExtra("board_title"), data.getStringExtra("board_content"))
                        presenterFeedbackDetail.addItems(arrayList,createBoardText, mAdapter)
                    }
                    Activity.RESULT_CANCELED -> Toast.makeText(this@FeedbackDetailActivity, "취소됨.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feedback_detail_menu, menu)
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){
            R.id.create_Post_Button -> { return create_Post_Button() }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }
    fun create_Post_Button(): Boolean {
        val intent = Intent(this, CreatePostActivity::class.java)
        intent.putExtra("feedback_id", feedback_id)
        startActivityForResult(intent, 111)
        return true
    }

    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }

}
