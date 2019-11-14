package com.example.remindfeedback.FeedbackList.FeedbackDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.Alarm.AdapterAlarm
import com.example.remindfeedback.Alarm.ModelAlarm
import com.example.remindfeedback.Alarm.PresenterAlarm
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.android.synthetic.main.activity_feedback_detail.*

class FeedbackDetailActivity : AppCompatActivity() , ContractFeedbackDetail.View{
    private val TAG = "FeedbackDetailActivity"
    internal lateinit var presenterFeedbackDetail: PresenterFeedbackDetail
    var arrayList = arrayListOf<ModelFeedbackDetail>(
        ModelFeedbackDetail("사진", "목요일반 수업 발표연습", "2019년 9월 31일 오후 3시 30분", 5),
        ModelFeedbackDetail("글", "목요일반 수업 발표연습", "2019년 9월 31일 오후 3시 30분", 5),
        ModelFeedbackDetail("녹음", "목요일반 수업 발표연습", "2019년 9월 31일 오후 3시 30분", 5),
        ModelFeedbackDetail("비디오", "목요일반 수업 발표연습", "2019년 9월 31일 오후 3시 30분", 5)



    )
    val mAdapter = AdapterFeedbackDetail(this, arrayList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_detail)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        feedback_Detail_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        feedback_Detail_Recyclerview.layoutManager = lm
        feedback_Detail_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        presenterFeedbackDetail = PresenterFeedbackDetail().apply {
            view = this@FeedbackDetailActivity
        }
        //presenterFeedbackDetail.loadItems(arrayList)
    }


    override fun refresh() {

    }
}
