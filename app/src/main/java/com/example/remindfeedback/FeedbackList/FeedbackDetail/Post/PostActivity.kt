package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Intent
import android.graphics.Bitmap
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.Alarm.AdapterAlarm
import com.example.remindfeedback.Alarm.ModelAlarm
import com.example.remindfeedback.Alarm.PresenterAlarm
import com.example.remindfeedback.FeedbackList.FeedbackDetail.PresenterFeedbackDetail
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import com.example.remindfeedback.etcProcess.URLtoBitmapTask
import kotlinx.android.synthetic.main.activity_feedback_detail.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.activity_post.*
import java.net.URL

class PostActivity : AppCompatActivity(), ContractPost.View {


    private val TAG = "PostActivity"
    internal lateinit var presenterPost: PresenterPost
    lateinit var imageData:String
    var arrayList = arrayListOf<ModelPost>(
        //ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1)
        )
    val mAdapter = AdapterPost(this, arrayList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        //ab.setDisplayHomeAsUpEnabled(true)

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        post_Comment_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        post_Comment_Recyclerview.layoutManager = lm
        post_Comment_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        presenterPost = PresenterPost().apply {
            view = this@PostActivity
            mContext = this@PostActivity
        }

        var intent:Intent= intent
        presenterPost.typeInit(intent.getIntExtra("feedback_id", -1), intent.getIntExtra("board_id", -1))
            //댓글다는 부분
        comment_Commit_Button.setOnClickListener {
            if(!comment_EditText.text.toString().equals("")){
                presenterPost.addItems(mAdapter, comment_EditText.text.toString())
                comment_EditText.setText("")
            }

        }

    }

    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }
    override fun setView(contentsType: Int, fileUrl_1: String?, fileUrl_2: String?, fileUrl_3: String?) {
        if(contentsType == 0){//타입이 글일때
            post_Picture.visibility = View.GONE
            post_Video.visibility = View.GONE
        }else if(contentsType == 1){//타입이 사진일때
            post_Video.visibility = View.GONE
            //이미지 설정해주는 부분
            var image_task: URLtoBitmapTask = URLtoBitmapTask()
            image_task = URLtoBitmapTask().apply {
                url = URL("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_1)
                imageData = "https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_1
            }
            var bitmap: Bitmap = image_task.execute().get()
            post_Picture.setImageBitmap(bitmap)

        }else if(contentsType == 2){//타입이 비디오일때
            post_Picture.visibility = View.GONE
        }

    }


}
