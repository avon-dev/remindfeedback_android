package com.example.remindfeedback.FeedbackList.FeedbackDetail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost.CreatePostActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.CreateBoardPicture
import com.example.remindfeedback.ServerModel.CreateBoardText
import com.example.remindfeedback.ServerModel.CreateboardRecord
import com.example.remindfeedback.ServerModel.CreateboardVideo
import com.rey.material.app.BottomSheetDialog
import com.rey.material.drawable.ThemeDrawable
import com.rey.material.util.ViewUtil
import com.rey.material.widget.Button
import kotlinx.android.synthetic.main.activity_feedback_detail.*

class FeedbackDetailActivity : AppCompatActivity(), ContractFeedbackDetail.View {


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

    var arrayList = arrayListOf<ModelFeedbackDetail>(
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_detail)


        //액션바 설정
        var ab: ActionBar = this.supportActionBar!!
        ab.setTitle(" ")
        // 액션바 타이틀 가운데 정렬
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.actionbar_title)
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

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
        feedback_Detail_Title_Tv.text = "[" + intent.getStringExtra("title") + "]에 대한 피드백"
        feedback_Detail_Date_Tv.text = "목표일 : " + intent.getStringExtra("date")


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
    }

    //아래에서 올라오는 뷰 만드는부분(바텀시트)
    private fun showAdviserBottomSheet() {
        mBottomSheetDialog = BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog)
        val v = LayoutInflater.from(this).inflate(R.layout.view_adviser_bottomsheet, null)
        ViewUtil.setBackground(v, ThemeDrawable(R.drawable.bg_window_light))
        val bottom_Sheet_Title = v.findViewById<View>(R.id.bottom_Sheet_Title) as TextView
        bottom_Sheet_Title.setText("완료요청을 수락 하시겠습니까?")
        val bt_accept = v.findViewById<View>(R.id.sheet_bt_Accept) as Button
        val bt_reject = v.findViewById<View>(R.id.sheet_bt_Reject) as Button
        val bt_wrap = v.findViewById<View>(R.id.sheet_bt_wrap) as Button

        bt_accept.setText("수락하기")
        bt_accept.setOnClickListener {
            when(feedback_complete){
                1 -> { presenterFeedbackDetail.completeAccept(feedback_id) }
                -1, 0 -> { Toast.makeText(this, "완료요청 되지않은 피드백입니다.", Toast.LENGTH_LONG).show() }
                2 -> { Toast.makeText(this, "이미 완료된 피드백입니다.", Toast.LENGTH_LONG).show() }
            }
            mBottomSheetDialog!!.dismissImmediately()
        }

        bt_reject.setText("거절하기")
        bt_reject.setOnClickListener {
            when(feedback_complete){
                1 -> { presenterFeedbackDetail.completeReject(feedback_id) }
                0 -> { Toast.makeText(this, "이미 거절한 피드백입니다.", Toast.LENGTH_LONG).show() }
                -1 -> { Toast.makeText(this, "완료 요청되지 않은 피드백입니다.", Toast.LENGTH_LONG).show() }
                2 -> { Toast.makeText(this, "이미 완료된 피드백입니다.", Toast.LENGTH_LONG).show() }

            }

            mBottomSheetDialog!!.dismissImmediately()
        }

        bt_wrap.setText("취소")
        bt_wrap.setOnClickListener { mBottomSheetDialog!!.dismissImmediately() }
        v.setBackgroundColor(Color.parseColor("#ffffff"))
        bt_accept.setBackgroundColor(Color.parseColor("#ddeeff"))
        bt_reject.setBackgroundColor(Color.parseColor("#ddeeff"))
        bt_wrap.setBackgroundColor(Color.parseColor("#ddeeff"))

        mBottomSheetDialog!!.contentView(v)
            .show()
    }

    //아래에서 올라오는 뷰 만드는부분(바텀시트)
    private fun showBottomSheet() {
        mBottomSheetDialog = BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog)
        val v = LayoutInflater.from(this).inflate(R.layout.view_bottomsheet, null)
        ViewUtil.setBackground(v, ThemeDrawable(R.drawable.bg_window_light))
        val bottom_Sheet_Title = v.findViewById<View>(R.id.bottom_Sheet_Title) as TextView
        bottom_Sheet_Title.setText("완료요청 하시겠습니까?")
        val bt_match = v.findViewById<View>(R.id.sheet_bt_match) as Button
        bt_match.setText("확인")
        bt_match.setOnClickListener {
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
            mBottomSheetDialog!!.dismissImmediately()
        }
        val bt_wrap = v.findViewById<View>(R.id.sheet_bt_wrap) as Button
        bt_wrap.setText("취소")
        bt_wrap.setOnClickListener { mBottomSheetDialog!!.dismissImmediately() }
        v.setBackgroundColor(Color.parseColor("#ffffff"))
        bt_match.setBackgroundColor(Color.parseColor("#ddeeff"))
        bt_wrap.setBackgroundColor(Color.parseColor("#ddeeff"))

        mBottomSheetDialog!!.contentView(v)
            .show()
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
                    Activity.RESULT_CANCELED -> Toast.makeText(
                        this@FeedbackDetailActivity,
                        "취소됨.",
                        Toast.LENGTH_SHORT
                    ).show()
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
                    Activity.RESULT_CANCELED -> Toast.makeText(
                        this,
                        "취소됨.",
                        Toast.LENGTH_SHORT
                    ).show()
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
        Log.e("보드 타입 (board_category)", board_category.toString())
        Log.e("보드 수정할 데이터1 (feedback_Id)", feedback_id.toString())
        Log.e("보드 수정할 데이터2 (board_id)", board_id.toString())
        Log.e("보드 수정할 데이터3 (board_title)", board_title)
        Log.e("보드 수정할 데이터4 (board_content)", board_content)
        startActivityForResult(intent, 112)
    }

    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        when (feedbackMyYour) {
            0 -> {//내꺼
                menuInflater.inflate(R.menu.feedback_detail_menu, menu)
            }
            1 -> {//다른사람꺼
                Log.e("feedback_complete", feedback_complete.toString())
                when(feedback_complete){
                    1 -> {
                        menuInflater.inflate(R.menu.feedback_detail_adviser_menu, menu)
                    }
                }
            }
        }
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when (item.itemId) {
            R.id.create_Post_Button -> {
                return create_Post_Button()
            }
            R.id.complete_Request_Button -> {
                return complete_Request_Button()
            }
            R.id.about_Complete_Button -> {
                return  about_Complete_Button()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun create_Post_Button(): Boolean {
        val intent = Intent(this, CreatePostActivity::class.java)
        intent.putExtra("feedback_id", feedback_id)
        startActivityForResult(intent, 111)
        return true
    }
    //완료요청
    fun complete_Request_Button(): Boolean {
        showBottomSheet()
        return true
    }
    //완료에대한 바텀시트
    fun about_Complete_Button(): Boolean {
        showAdviserBottomSheet()

        return true
    }


    //프레젠터에서 컴플리트값을 바꿔줌
    override fun setFeedbackComplete(mFeedbackComplete:Int){
        feedback_complete = mFeedbackComplete
    }

    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog!!.dismissImmediately()
            mBottomSheetDialog = null
        }
    }


}
