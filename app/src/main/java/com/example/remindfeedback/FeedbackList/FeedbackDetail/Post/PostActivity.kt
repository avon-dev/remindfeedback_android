package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.Alarm.AdapterAlarm
import com.example.remindfeedback.Alarm.ModelAlarm
import com.example.remindfeedback.Alarm.PresenterAlarm
import com.example.remindfeedback.FeedbackList.FeedbackDetail.PresenterFeedbackDetail
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_feedback_detail.*
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity(), ContractPost.View {
    private val TAG = "PostActivity"
    internal lateinit var presenterPost: PresenterPost
    var arrayList = arrayListOf<ModelPost>(
        ModelPost("a","a","a","a",2),
        ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1),
        ModelPost("dummy", "3월김수미", "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라만세 무궁화 삼천리 화려강산 대한사람", "2019년 10월 30일 오전 7시 41분", 1),
        ModelPost("dummy", "3월김수미", "으베으ㅔ에에ㅔㅔ베에ㅔ에ㅔ에ㅔㅔ에으으에ㅔ베ㅔ에ㅔ에ㅔ에ㅔ", "2019년 10월 30일 오전 7시 41분", 1),
        ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1),
        ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1),
        ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1),
        ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1),
        ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1),
        ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1),
        ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1)

    )
    val mAdapter = AdapterPost(this, arrayList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        post_Comment_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        post_Comment_Recyclerview.layoutManager = lm
        post_Comment_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        presenterPost = PresenterPost().apply {
            view = this@PostActivity
        }

    }

    override fun refresh() {

    }



}
