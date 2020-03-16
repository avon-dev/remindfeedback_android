package com.avon.remindfeedback.FeedbackList.FeedbackDetail

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.avon.remindfeedback.etcProcess.Fab
import com.avon.remindfeedback.FeedbackList.FeedbackDetail.CreatePost.CreatePostActivity
import com.avon.remindfeedback.R
import com.avon.remindfeedback.ServerModel.CreateBoardPicture
import com.avon.remindfeedback.ServerModel.CreateBoardText
import com.avon.remindfeedback.ServerModel.CreateboardRecord
import com.avon.remindfeedback.ServerModel.CreateboardVideo
import com.avon.remindfeedback.etcProcess.TutorialFrame
import com.rey.material.app.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_feedback_detail.*
import com.gordonwong.materialsheetfab.MaterialSheetFab
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener
import kotlinx.android.synthetic.main.new_floating.*

class FeedbackDetailActivity : AppCompatActivity(), ContractFeedbackDetail.View , View.OnClickListener{


    private val TAG = "FeedbackDetailActivity"
    internal lateinit var presenterFeedbackDetail: PresenterFeedbackDetail
    var feedback_id: Int = -1
    var feedbackMyYour: Int = -1
    var feedback_complete:Int = -2
    var feedback_adviser:String = "" //어드바이저가 있는지 판단해서 완료요청을 할지 아니면 바로 완료로 옮겨버릴지 판단
    lateinit var mAdapter: AdapterFeedbackDetail
    var photoBoolean:Boolean = true
    var textBoolean:Boolean = true
    var mBottomSheetDialog:BottomSheetDialog? = null


    //fab라이브러리
    private var materialSheetFab: MaterialSheetFab<*>? = null
    private var statusBarColor: Int = 0

    var tutorialCount:Int = 0
    internal lateinit var preferences: SharedPreferences

    var arrayList = arrayListOf<ModelFeedbackDetail>(
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_detail)


        //액션바 설정
        var ab: ActionBar = this.supportActionBar!!
        ab.setTitle("")
        // 액션바 타이틀 가운데 정렬
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.actionbar_title)

        preferences = getSharedPreferences("USERSIGN", 0)

        //새로운 fab설정하는 부분

        presenterFeedbackDetail = PresenterFeedbackDetail().apply {
            view = this@FeedbackDetailActivity
            mContext = this@FeedbackDetailActivity
        }

        val intent = intent
        feedback_id = intent.getIntExtra("feedback_id", -1)
        feedbackMyYour = intent.getIntExtra("feedbackMyYour", -1)
        feedback_complete = intent.getIntExtra("complete", -2)
        feedback_adviser = intent.getStringExtra("adviser")
        Log.e("넘어온 feedbackMyYour", feedbackMyYour.toString())

        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        if (feedbackMyYour == 0) {
            ab.setDisplayHomeAsUpEnabled(true)
        }

        feedback_Detail_Title_Tv.text = "[" + intent.getStringExtra("title") + "]에 대한 피드백"
        feedback_Detail_Date_Tv.text = "목표일 : " + intent.getStringExtra("date")
        setupFab()

        mAdapter = AdapterFeedbackDetail(this, arrayList, presenterFeedbackDetail, feedbackMyYour)

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        feedback_Detail_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        feedback_Detail_Recyclerview.layoutManager = lm
        feedback_Detail_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌


        presenterFeedbackDetail.loadItems(arrayList, mAdapter, feedback_id, photoBoolean, textBoolean)


        photo_Iv.setOnClickListener{
            if(photoBoolean == true){
                photoBoolean = false
                photo_Iv.setImageResource(R.drawable.ic_photo_alpha)
                presenterFeedbackDetail.loadItems(arrayList, mAdapter, feedback_id, photoBoolean, textBoolean)
            }else{
                photoBoolean = true
                photo_Iv.setImageResource(R.drawable.ic_photo_black)
                presenterFeedbackDetail.loadItems(arrayList, mAdapter, feedback_id, photoBoolean, textBoolean)
            }
        }

        text_Iv.setOnClickListener{
            if(textBoolean == true){
                textBoolean = false
                text_Iv.setImageResource(R.drawable.ic_text_alpha)
                presenterFeedbackDetail.loadItems(arrayList, mAdapter, feedback_id, photoBoolean, textBoolean)
            }else{
                textBoolean = true
                text_Iv.setImageResource(R.drawable.ic_text)
                presenterFeedbackDetail.loadItems(arrayList, mAdapter, feedback_id, photoBoolean, textBoolean)
            }
        }

        firstRunCheck()
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            111 -> {    // 보드 추가 후 돌아왔을 때
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        if (data.getIntExtra("return_type", -1) == 0) {//글일때
                            val createBoardText = CreateBoardText(
                                data.getIntExtra("feedback_id", -1),
                                data.getStringExtra("board_title"),
                                data.getStringExtra("board_content")
                            )
                            presenterFeedbackDetail.addTextItems(
                                arrayList,
                                createBoardText,
                                mAdapter
                            )
                        } else if (data.getIntExtra("return_type", -1) == 1) {//사진일때
                            val createBoardPicture = CreateBoardPicture(
                                data.getIntExtra("feedback_id", -1),
                                data.getStringExtra("board_title"),
                                data.getStringExtra("board_content"),
                                data.getStringExtra("file1_uri"),
                                data.getStringExtra("file2_uri"),
                                data.getStringExtra("file3_uri")
                            )
                            presenterFeedbackDetail.addPictureItems(
                                arrayList,
                                createBoardPicture,
                                mAdapter
                            )
                        } else if (data.getIntExtra("return_type", -1) == 2) {//동영상일때
                            val createboardVideo = CreateboardVideo(
                                data.getIntExtra("feedback_id", -1),
                                data.getStringExtra("board_title"),
                                data.getStringExtra("board_content"),
                                data.getStringExtra("video_uri")
                            )
                            presenterFeedbackDetail.addVideoItems(
                                arrayList,
                                createboardVideo,
                                mAdapter
                            )
                        } else if (data.getIntExtra("return_type", -1) == 3) {//녹음일때
                            val createboardRecord = CreateboardRecord(
                                data.getIntExtra("feedback_id", -1),
                                data.getStringExtra("board_title"),
                                data.getStringExtra("board_content"),
                                data.getStringExtra("record_uri")
                            )
                            presenterFeedbackDetail.addRecordItems(
                                arrayList,
                                createboardRecord,
                                mAdapter
                            )
                        }

                    }

                }
            }
            112 -> {    // 보드 수정 후 돌아왔을 때
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        if (data.getIntExtra("return_type", -1) == 0) {    // 글
                            val modifyBoardText = CreateBoardText(
                                data.getIntExtra("feedback_id", -1),
                                data.getStringExtra("board_title"),
                                data.getStringExtra("board_content")
                            )
                            presenterFeedbackDetail.updateTextItems(
                                arrayList,
                                data.getIntExtra("board_id", -1),
                                modifyBoardText,
                                mAdapter
                            )
                        } else if (data.getIntExtra("return_type", -1) == 1) { // 사진
                            val modifyBoardPicture = CreateBoardPicture(
                                data.getIntExtra("feedback_id", -1),
                                data.getStringExtra("board_title"),
                                data.getStringExtra("board_content"),
                                data.getStringExtra("file1_uri"),
                                data.getStringExtra("file2_uri"),
                                data.getStringExtra("file3_uri")
                            )
                            presenterFeedbackDetail.updatePictureItems(
                                arrayList,
                                data.getIntExtra("board_id", -1),
                                modifyBoardPicture,
                                mAdapter
                            )
                        }
                    }
                }
            }
        }
    }

    // 수정화면
    override fun modifyBoardActivity(
        feedback_id: Int,
        board_id: Int,
        board_category: Int,
        board_title: String,
        board_content: String
    ) {
        val intent = Intent(this, CreatePostActivity::class.java)
        intent.putExtra("feedback_id", feedback_id)
        intent.putExtra("board_id", board_id)
        intent.putExtra("title", board_title)
        intent.putExtra("content", board_content)
        intent.putExtra("board_category", board_category)
        startActivityForResult(intent, 112)
    }





    //완료요청
    fun complete_Request_Button() {
        when(feedback_complete) {
            -1, 0 -> {
                if (feedback_adviser.equals("")) {
                    presenterFeedbackDetail.completeAccept(feedback_id)//조언자가 없는 피드백이라 그냥 수락하게 함
                } else {
                    presenterFeedbackDetail.completeRequest(feedback_id)
                }
            }
            1 -> {
                Toast.makeText(this, "이미 완료 요청된 피드백 입니다.", Toast.LENGTH_LONG).show()
            }
            2 -> {
                Toast.makeText(this, "이미 완료된 피드백 입니다.", Toast.LENGTH_LONG).show()
            }
        }


    }

    //완료요청을 수락
    fun complete_Accept_Button(){
        when(feedback_complete){
            1 -> { presenterFeedbackDetail.completeAccept(feedback_id) }
            -1, 0 -> { Toast.makeText(this, "완료요청 되지않은 피드백입니다.", Toast.LENGTH_LONG).show() }
            2 -> { Toast.makeText(this, "이미 완료된 피드백입니다.", Toast.LENGTH_LONG).show() }
        }
    }

    //완료요청을 거절
    fun complete_Reject_Button(){
        when(feedback_complete){
            1 -> { presenterFeedbackDetail.completeReject(feedback_id) }
            0 -> { Toast.makeText(this, "이미 거절한 피드백입니다.", Toast.LENGTH_LONG).show() }
            -1 -> { Toast.makeText(this, "완료 요청되지 않은 피드백입니다.", Toast.LENGTH_LONG).show() }
            2 -> { Toast.makeText(this, "이미 완료된 피드백입니다.", Toast.LENGTH_LONG).show() }
        }
    }
    //완료요청하기
    fun complete_Create_Button() {
        val intent = Intent(this, CreatePostActivity::class.java)
        intent.putExtra("feedback_id", feedback_id)
        startActivityForResult(intent, 111)

    }


    //프레젠터에서 컴플리트값을 바꿔줌
    override fun setFeedbackComplete(mFeedbackComplete:Int){
        feedback_complete = mFeedbackComplete
    }

    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }
    //첫번째인지 체크
    fun firstRunCheck(){
        var isFirst:Boolean = preferences.getBoolean("firstFeedbackDetailActivity", true);
        if(isFirst){
            startTutorial()
        }
    }
    //튜토리얼 진행
    fun startTutorial(){
        when(tutorialCount){
            0 -> {var tframe = TutorialFrame("피드백에 관한 작업을 수행합니다.", "안녕하세요!", findViewById<View>(R.id.fab), this, { startTutorial()})
                tutorialCount++
                tframe.mTutorial()}
            1 -> {var tframe = TutorialFrame("보고자 하는 컨텐츠만 필터링 할 수 있습니다.", "안녕하세요!", findViewById<View>(R.id.photo_Iv), this, { startTutorial()})
                tutorialCount++
                tframe.mTutorial()}
            2 -> {
                preferences.edit().putBoolean("firstFeedbackDetailActivity", false).apply()
            }

        }

    }

    private fun setupFab() {

        val fab = findViewById<View>(R.id.fab) as? Fab
        val sheetView = findViewById<View>(R.id.fab_sheet)
        val overlay = findViewById<View>(R.id.overlay)
        val sheetColor = resources.getColor(R.color.background_card)
        val fabColor = resources.getColor(R.color.theme_accent)


        // Create material sheet FAB
        materialSheetFab = MaterialSheetFab(fab, sheetView, overlay, sheetColor, fabColor)
        // Set material sheet event listener
        materialSheetFab!!.setEventListener(object : MaterialSheetFabEventListener() {
            override fun onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor()
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(resources.getColor(R.color.colorPrimary))
            }

            override fun onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor)
            }
        })

        Log.e("feedbackMyYour", feedbackMyYour.toString())
        Log.e("feedback_complete", feedback_complete.toString())
        //내껀지 상대방껀지 판단해주는 부분
        when (feedbackMyYour) {
            0 -> {//내꺼
                fab_Sheet_Item_Reject.visibility = View.GONE
                fab_Sheet_Item_Accept.visibility = View.GONE
            }
            1 -> {//다른사람꺼
                when(feedback_complete){
                    1 -> {
                        fab_Sheet_Item_Create.visibility = View.GONE
                        fab_Sheet_Item_Request.visibility = View.GONE
                    }
                    else -> {
                        fab!!.visibility = View.GONE
                    }
                }
            }
        }

        findViewById<View>(R.id.fab_Sheet_Item_Request).setOnClickListener(this)
        findViewById<View>(R.id.fab_Sheet_Item_Reject).setOnClickListener(this)
        findViewById<View>(R.id.fab_Sheet_Item_Accept).setOnClickListener(this)
        findViewById<View>(R.id.fab_Sheet_Item_Create).setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v!!.getId()){
            R.id.fab_Sheet_Item_Request -> {
                complete_Request_Button()
            }
            R.id.fab_Sheet_Item_Reject -> {
                complete_Reject_Button()
            }
            R.id.fab_Sheet_Item_Accept -> {
                complete_Accept_Button()
            }
            R.id.fab_Sheet_Item_Create -> {
                complete_Create_Button()
            }
        }

        materialSheetFab!!.hideSheet()
    }


    private fun getStatusBarColor(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor
        } else 0
    }

    private fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = color
        }
    }

}
