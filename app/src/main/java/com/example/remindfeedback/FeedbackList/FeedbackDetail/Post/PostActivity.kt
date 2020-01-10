package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.remindfeedback.Alarm.AdapterAlarm
import com.example.remindfeedback.Alarm.ModelAlarm
import com.example.remindfeedback.Alarm.PresenterAlarm
import com.example.remindfeedback.FeedbackList.FeedbackDetail.PresenterFeedbackDetail
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import com.example.remindfeedback.ServerModel.CreateComment
import com.example.remindfeedback.etcProcess.URLtoBitmapTask
import kotlinx.android.synthetic.main.activity_feedback_detail.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_my_page.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import android.support.v4.media.session.MediaControllerCompat.setMediaController
import android.view.*
import android.widget.*
import androidx.core.content.FileProvider
import com.example.remindfeedback.FeedbackList.MainActivity
import java.io.File
import android.R.attr.start
import com.google.android.exoplayer2.ExoPlayerFactory
import kotlinx.android.synthetic.main.activity_post.*
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util


class PostActivity : AppCompatActivity(), ContractPost.View, ViewPager.OnPageChangeListener{



    private val TAG = "PostActivity"
    internal lateinit var presenterPost: PresenterPost
    lateinit var imageData:String
    var video_name:String? = ""

    private var mViewPager: ViewPager? = null
    private var mViewPagerAdapter: ViewPagerAdapter? = null
    private var mLinearLayout: LinearLayout? = null
    lateinit var indicators: Array<ImageView?>
    lateinit var mJSONArray: JSONArray
    private var ALBUM_NUM = 0
    private var ALBUM_RES = arrayListOf<String>()
    //게시물 아이디
    var board_id :Int = -1

    //exoplayer변수
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L


    var arrayList = arrayListOf<ModelComment>(
        //ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1)
        )
    val mAdapter = AdapterPost(this, arrayList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //액션바 설정
        var ab: ActionBar = this.supportActionBar!!
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
        board_id = intent.getIntExtra("board_id", -1)
        presenterPost.typeInit(intent.getIntExtra("feedback_id", -1), intent.getIntExtra("board_id", -1))
        presenterPost.getComment(arrayList, mAdapter, board_id)

        //댓글다는 부분
        comment_Commit_Button.setOnClickListener {
            if(!comment_EditText.text.toString().equals("")){
                presenterPost.addComment(mAdapter, CreateComment(board_id, comment_EditText.text.toString()), arrayList)
                comment_EditText.setText("")
            }

        }

    }

    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }
    //포스팅의 컨텐츠 타입과 파일들이 넘어옴
    override fun setView(contentsType: Int, fileUrl_1: String?, fileUrl_2: String?, fileUrl_3: String?, title:String, date:String, content:String) {
        if(contentsType == 0){//타입이 글일때
            post_Title_Tv.text = "[글]"
            post_Picture.visibility = View.GONE
            exoPlayerView.visibility = View.GONE
        }else if(contentsType == 1){//타입이 사진일때
            exoPlayerView.visibility = View.GONE
            post_Title_Tv.text = "[사진]"
            //파일이 있으면 넘버에 추가
            if(fileUrl_1 != null){
                ALBUM_RES.add("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_1)
                ALBUM_NUM++
            }
            if(fileUrl_2 != null){
                ALBUM_RES.add("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_2)
                ALBUM_NUM++
            }
            if(fileUrl_3 != null){
                ALBUM_RES.add("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+fileUrl_3)
                ALBUM_NUM++
            }
            viewPagerSetting()

        }else if(contentsType == 2){//타입이 비디오일때
            post_Title_Tv.text = "[영상]"
            post_Picture.visibility = View.GONE
            video_name = fileUrl_1
            initializePlayer(video_name)




        }else if(contentsType == 3){//타입이 녹음일때
            post_Title_Tv.text = "[음성]"

        }else{
            Toast.makeText(this@PostActivity, "글을 불러올수 없습니다. 관리자에게 문의하세요.", Toast.LENGTH_SHORT).show()
            finish()
        }
        post_Title_Tv.text = post_Title_Tv.text.toString() + title
        post_Date_Tv.text = date
        post_Tv.text = content
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

    fun initializePlayer(filename:String?){
        if(player == null){
            player = ExoPlayerFactory.newSimpleInstance(this.getApplicationContext());
            exoPlayerView!!
                .setPlayer(player);
        }
        var video_url:String = "http://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+filename
        var mediaSource:MediaSource = buildMediaSource(Uri.parse(video_url))
        //준비
        player!!.prepare(mediaSource, true, false)
        //스타트, 스탑
        player!!.playWhenReady.and(playWhenReady)


    }

    fun buildMediaSource(uri: Uri) : MediaSource{
        var userAgent:String = Util.getUserAgent(this, "remindfeedback")
        if(uri.getLastPathSegment().contains("mp3") || uri.getLastPathSegment().contains("mp4")){
            return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri)
        }else if(uri.getLastPathSegment().contains("m3u8")){
            return HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri)
        }else{
            return ExtractorMediaSource.Factory(DefaultDataSourceFactory(this, userAgent)).createMediaSource(uri)
        }
    }
    fun releasePlayer(){
        if(player != null){
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            playWhenReady = player!!.playWhenReady
            exoPlayerView!!.setPlayer(null)
            player!!.release()
            player = null
        }
    }
    override fun onStop() {
        super.onStop()
        releasePlayer()
    }
}
