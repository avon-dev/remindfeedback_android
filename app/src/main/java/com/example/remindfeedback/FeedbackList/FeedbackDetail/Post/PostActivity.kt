package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Intent
import android.graphics.Bitmap
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class PostActivity : AppCompatActivity(), ContractPost.View, ViewPager.OnPageChangeListener {


    private val TAG = "PostActivity"
    internal lateinit var presenterPost: PresenterPost
    lateinit var imageData:String


    private var mViewPager: ViewPager? = null
    private var mViewPagerAdapter: ViewPagerAdapter? = null
    private var mLinearLayout: LinearLayout? = null
    lateinit var indicators: Array<ImageView?>
    lateinit var mJSONArray: JSONArray
    private var ALBUM_NUM = 0
    private var ALBUM_RES = arrayListOf<String>()


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
    //포스팅의 컨텐츠 타입과 파일들이 넘어옴
    override fun setView(contentsType: Int, fileUrl_1: String?, fileUrl_2: String?, fileUrl_3: String?) {
        if(contentsType == 0){//타입이 글일때
            post_Picture.visibility = View.GONE
            post_Video.visibility = View.GONE
        }else if(contentsType == 1){//타입이 사진일때
            post_Video.visibility = View.GONE

            /*
            //이미지 설정해주는 부분
            var image_task: URLtoBitmapTask = URLtoBitmapTask()
            image_task = URLtoBitmapTask().apply {
                url = URL("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_1)
                imageData = "https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_1
            }
            var bitmap: Bitmap = image_task.execute().get()
            post_Picture.setImageBitmap(bitmap)
*/


            //파일이 있으면 넘버에 추가
            if(fileUrl_1 != null){
                ALBUM_RES.add("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_1)
                ALBUM_NUM++
            }else if(fileUrl_2 != null){
                ALBUM_RES.add("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_2)
                ALBUM_NUM++
            }else if(fileUrl_3 != null){
                ALBUM_RES.add("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_3)
                ALBUM_NUM++
            }
            viewPagerSetting()



        }else if(contentsType == 2){//타입이 비디오일때
            post_Picture.visibility = View.GONE
        }

    }


    //이 아래로 뷰페이저 관련 코드들
    private fun setupDataSources() {
        mJSONArray = JSONArray()
        for (i in 0 until ALBUM_NUM) {
            val `object` = JSONObject()
            try {
                `object`.put("id", ALBUM_RES[i % ALBUM_RES.size])
                `object`.put("name", "Image dog$i")
                mJSONArray.put(`object`)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

    }
    override fun onPageScrolled(i: Int, v: Float, i1: Int) {

    }

    override fun onPageSelected(i: Int) {
        setImageIndicators(i)
    }

    override fun onPageScrollStateChanged(i: Int) {

    }

    override fun viewPagerSetting() {
        setupDataSources()
        mViewPager = findViewById(R.id.viewpager) as ViewPager
        mViewPagerAdapter = ViewPagerAdapter(this, mJSONArray)
        mLinearLayout = findViewById(R.id.viewGroup) as LinearLayout
        initialSetImageIndicators()
        mViewPager!!.adapter = mViewPagerAdapter
        mViewPager!!.setOnPageChangeListener(this)

    }

    private fun initialSetImageIndicators() {
        indicators = arrayOfNulls(ALBUM_NUM)
        for (i in 0 until ALBUM_NUM) {

            val imageView = ImageView(this)
            if (i == 0) {
                imageView.setImageResource(R.drawable.indicator_select)
            } else {
                imageView.setImageResource(R.drawable.indicator_idle)
            }
            val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.leftMargin = 5
            lp.rightMargin = 5
            indicators[i] = imageView
            mLinearLayout!!.addView(imageView, lp)
        }
    }

    private fun setImageIndicators(pos: Int) {
        for (i in 0 until ALBUM_NUM) {
            if (i == pos) {
                indicators[i]!!.setImageResource(R.drawable.indicator_select)
            } else {
                indicators[i]!!.setImageResource(R.drawable.indicator_idle)
            }
        }
    }


}
