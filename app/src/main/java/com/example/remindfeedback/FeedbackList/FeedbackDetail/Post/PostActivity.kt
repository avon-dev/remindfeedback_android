package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.R.attr.scaleType
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
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.remindfeedback.etcProcess.InfiniteScrollListener
import com.example.remindfeedback.etcProcess.MyProgress
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.activity_post.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.text.SimpleDateFormat


//class PostActivity : AppCompatActivity(), ContractPost.View, ViewPager.OnPageChangeListener, View.OnClickListener, View.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener{
class PostActivity : AppCompatActivity(), ContractPost.View, ViewPager.OnPageChangeListener {


    private val TAG = "PostActivity"
    internal lateinit var presenterPost: PresenterPost
    lateinit var imageData: String
    var file_name: String? = ""

    private var mViewPager: ViewPager? = null
    private var mViewPagerAdapter: ViewPagerAdapter? = null
    private var mLinearLayout: LinearLayout? = null
    lateinit var indicators: Array<ImageView?>
    lateinit var mJSONArray: JSONArray
    private var ALBUM_NUM = 0
    private var ALBUM_RES = arrayListOf<String>()
    //게시물 아이디
    var board_id: Int = -1
    lateinit var lm: LinearLayoutManager
    internal lateinit var preferences: SharedPreferences

    var sort:Int = -1;

    /* 영상 녹음관련 코드는 모두 주석
    //exoplayer변수
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    //레코드 관련 변수
    internal lateinit var play: Button
    internal lateinit var pause: Button
    internal lateinit var seekBar: SeekBar
    internal lateinit var mediaplayer: Player
    internal var flag: Int = 0
    //internal var url = "https://remindfeedback.s3.ap-northeast-2.amazonaws.com/record/15789743263191578974318001.mp3"
    internal lateinit var record_url: String
    internal lateinit var progressDialog: ProgressDialog
    private var mediaPlayer: MediaPlayer? = null
    private var lengthOfAudio: Int = 0
    private val handler = Handler()

*/
    var arrayList = arrayListOf<ModelComment>(
        //ModelPost("dummy", "3월김수미", "설명이 좀 더 친절하면 알아듣기 좋을 거 같아요.", "2019년 10월 30일 오전 7시 41분", 1)
    )
    lateinit var mAdapter: AdapterPost
    var commentLastId = 0
    lateinit var post_Comment_Recyclerview:RecyclerView

    //녹음 관련이라 주석
    //private val r = Runnable { updateSeekProgress() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        preferences = getSharedPreferences("USERSIGN", 0)
        sort = preferences.getInt("sort", 1);

        var intent: Intent = intent
        board_id = intent.getIntExtra("board_id", -1)

        presenterPost = PresenterPost().apply {
            view = this@PostActivity
            mContext = this@PostActivity
        }
        post_Comment_Recyclerview = findViewById(R.id.post_Comment_Recyclerview)
        setRecyclerView(post_Comment_Recyclerview)

        //액션바 설정
        var ab: ActionBar = this.supportActionBar!!

//        ab.setTitle("")
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.actionbar_title)
//        ab.setDisplayHomeAsUpEnabled(true)




        presenterPost.typeInit(
            intent.getIntExtra("feedback_id", -1),
            intent.getIntExtra("board_id", -1)
        )
        //댓글다는 부분
        comment_Commit_Button.setOnClickListener {
            if (!comment_EditText.text.toString().equals("")) {
                presenterPost.addComment(mAdapter, CreateComment(board_id, comment_EditText.text.toString()), arrayList,sort)
                comment_EditText.setText("")
            }
        }

    }

/*
    override fun onPause() {
        super.onPause()
        presenterPost = PresenterPost().apply {
            view = this@PostActivity
            mContext = this@PostActivity
        }
        setRecyclerView()
    }
*/
    override fun onRestart() {
        super.onRestart()
        arrayList.clear()
        commentLastId = 0

        setRecyclerView(post_Comment_Recyclerview)
    }


    fun setRecyclerView(recyclerView: RecyclerView){

        Log.e("setRecyclerView", "함수 들어옴 실행!")

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        lm = LinearLayoutManager(this)
        lm.reverseLayout = true
        lm.stackFromEnd = true
        recyclerView.layoutManager = lm
        mAdapter = AdapterPost(this, arrayList, presenterPost, board_id,sort)
        recyclerView.adapter = mAdapter
        recyclerView.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌
        /*
        recyclerView.clearOnScrollListeners()
        recyclerView.addOnScrollListener(InfiniteScrollListener({
            Log.e("InfiniteScrollListener", "스크롤실행!")
            loadItem()
        }
            , lm)
        )//갱신
        */
        loadItem()
    }

    override fun loadItem(){
       presenterPost.getComment(arrayList, mAdapter, board_id, commentLastId,sort)
    }

    //레코드 관련 코드라서 주석

    /*
    override fun onClick(v: View) {
        when (v.id) {

            R.id.post_Record_Play_Button -> if (flag == 0) {
                //  new Player().execute(URL);
                mediaplayer = Player()
                mediaplayer.execute(record_url)

            } else {
                if (mediaPlayer != null) {

                    playAudio()

                }
            }
            R.id.post_Record_Pause_Button -> pauseAudio()
            else -> {
            }

        }

    }
*/

    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }

    //포스팅의 컨텐츠 타입과 파일들이 넘어옴
    override fun setView(contentsType: Int, fileUrl_1: String?, fileUrl_2: String?, fileUrl_3: String?, title: String, date: String, content: String) {
        if (contentsType == 0) {//타입이 글일때
            post_Title_Tv.text = "[글]"
            post_Picture.visibility = View.GONE
            exoPlayerView.visibility = View.GONE
            post_Record.visibility = View.GONE
        } else if (contentsType == 1) {//타입이 사진일때
            exoPlayerView.visibility = View.GONE
            post_Record.visibility = View.GONE
            post_Title_Tv.text = "[사진]"
            //파일이 있으면 넘버에 추가
            if (fileUrl_1 != null) {
                ALBUM_RES.add("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/" + fileUrl_1)
                ALBUM_NUM++
            }
            if (fileUrl_2 != null) {
                ALBUM_RES.add("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/" + fileUrl_2)
                ALBUM_NUM++
            }
            if (fileUrl_3 != null) {
                ALBUM_RES.add("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/" + fileUrl_3)
                ALBUM_NUM++
            }
            viewPagerSetting()

        }
        /* 비디오와 녹음은 주석
        else if(contentsType == 2){//타입이 비디오일때
            post_Title_Tv.text = "[영상]"
            post_Picture.visibility = View.GONE
            post_Record.visibility = View.GONE
            file_name = fileUrl_1
            initializePlayer(file_name)
        }else if(contentsType == 3){//타입이 녹음일때
            post_Title_Tv.text = "[음성]"
            post_Picture.visibility = View.GONE
            exoPlayerView.visibility = View.GONE
            file_name = fileUrl_1
            record_url = "https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+file_name
            recordSet()
        }
        */
        else {
            Toast.makeText(this@PostActivity, "글을 불러올수 없습니다. 관리자에게 문의하세요.", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
        post_Title_Tv.text = post_Title_Tv.text.toString() + " " + title

        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date)
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일") //new format
        val dateNewFormat = sdf.format(date)

        post_Date_Tv.text = "작성일 : " + dateNewFormat
        post_Tv.text = content
    }

    //프레젠터에서 아이템 로드하고 댓글의 라스트 id를 바꿔주는부분
    override fun setCommentId(comment_id:Int){
        Log.e("setCommentId", comment_id.toString())
        commentLastId = comment_id
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
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            if (i == 0) {
                imageView.setImageResource(R.drawable.indicator_select)
            } else {
                imageView.setImageResource(R.drawable.indicator_idle)
            }
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
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


    /*
    //비디오관련 코드
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


    //레코드 관련 코드
    override fun onBufferingUpdate(mediaPlayer: MediaPlayer, i: Int) {
        seekBar.secondaryProgress = i
        //println(i)

    }

    override fun onCompletion(mediaPlayer: MediaPlayer) {
        seekBar.progress = 0
    }

    override fun onInfo(mp: MediaPlayer, what: Int, extra: Int): Boolean {

        when (what) {
            MediaPlayer.MEDIA_INFO_BUFFERING_START -> progressDialog.show()
            MediaPlayer.MEDIA_INFO_BUFFERING_END -> progressDialog.dismiss()
        }
        return true
    }

    override fun onTouch(v: View, motionEvent: MotionEvent): Boolean {

        if (mediaPlayer!!.isPlaying) {
            val tmpSeekBar = v as SeekBar
            mediaPlayer!!.seekTo(lengthOfAudio / 100 * tmpSeekBar.progress)
        } else {
            val tmpSeekBar = v as SeekBar
            mediaPlayer!!.seekTo(lengthOfAudio / 100 * tmpSeekBar.progress)
        }
        return false
    }


    private fun updateSeekProgress() {
        if (mediaPlayer != null) {
            if (mediaPlayer!!.isPlaying) {
                seekBar.progress = (mediaPlayer!!.currentPosition.toFloat() / lengthOfAudio * 100).toInt()


                handler.postDelayed(r, 1000)
            }
        }
    }

    internal inner class Player : AsyncTask<String, Void, Boolean>() {
        override fun doInBackground(vararg params: String): Boolean? {

            var prepared: Boolean?

            try {
                mediaPlayer!!.setDataSource(params[0])
                mediaPlayer!!.prepare()
                lengthOfAudio = mediaPlayer!!.duration

                prepared = true

            } catch (e: IllegalArgumentException) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.message)
                prepared = false
                e.printStackTrace()
            } catch (e: SecurityException) {
                // TODO Auto-generated catch block
                prepared = false
                e.printStackTrace()
            } catch (e: IllegalStateException) {
                // TODO Auto-generated catch block
                prepared = false
                e.printStackTrace()
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                prepared = false
                e.printStackTrace()
            }

            return prepared
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog.show()

        }

        override fun onPostExecute(aBoolean: Boolean?) {
            super.onPostExecute(aBoolean)
            progressDialog.dismiss()
            if (aBoolean!!) {
                flag = 1
            } else {
                flag = 0
            }
            playAudio()

        }
    }

    private fun playAudio() {

        if (mediaPlayer != null) {
            mediaPlayer!!.start()
            updateSeekProgress()


        }
    }

    private fun pauseAudio() {
        if (mediaPlayer != null) {
            mediaPlayer!!.pause()
        }
    }
    */
}
