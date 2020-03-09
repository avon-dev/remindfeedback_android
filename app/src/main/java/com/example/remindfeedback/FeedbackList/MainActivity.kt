package com.example.remindfeedback.FeedbackList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.LoginFilter
import android.text.SpannableString
import android.util.Log
import android.view.Display
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.Alarm.AlarmActivity
import com.example.remindfeedback.CategorySetting.CategorySettingActivity
import com.example.remindfeedback.FeedbackList.CreateFeedback.CreateFeedbackActivity
import com.example.remindfeedback.FeedbackList.CreateFeedback.PickCategory.ModelPickCategory
import com.example.remindfeedback.FeedbackList.CreateFeedback.PickCategory.PickCategoryActivity
import com.example.remindfeedback.FriendsList.FriendsListActivity
import com.example.remindfeedback.Login.LoginActivity
import com.example.remindfeedback.MyPage.MyPageActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.Setting.SettingActivity
import com.example.remindfeedback.etcProcess.*
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.mxn.soul.flowingdrawer_core.ElasticDrawer
import com.mxn.soul.flowingdrawer_core.ElasticDrawer.STATE_OPEN
import com.mxn.soul.flowingdrawer_core.FlowingDrawer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_create_feedback.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ContractMain.View {

    private val TAG = "MainActivity"
    internal lateinit var presenterMain: PresenterMain
    var arrayList = arrayListOf<ModelFeedback?>()
    lateinit var mAdapter: AdapterMainFeedback
    var feedback_count: Int = 0
    var feedbackMyYour = 0//피드백이 내가 요청한건지 요청 받은건지 0이면 내가 요청한것, 1이면 요청 받은것
    var feedbackIngEd = 0//피드백이 진행중인지 진행완료인지 0이면 진행중인것, 1이면 진행 완료인것
    var feedbackIsFiltering = 0//필터링하고있는건지 아닌지 0이면 필터링아니고 일반, 1이면 필터링 중인것
    var selectedCategoryId = -2
    //뒤로가기시 경고문 띄울때
    private var lastTimeBackPressed:Long=-1500
    lateinit var ab: ActionBar

    lateinit var friends_List: TextView
    lateinit var request_Feedback: TextView
    lateinit var receive_Feedback: TextView
    lateinit var category_Setting: TextView
    lateinit var mypage: TextView
    lateinit var setting: TextView
    lateinit var logout: TextView

    lateinit var nav_Nickname_Tv:TextView
    lateinit var nav_Portrait_Iv:ImageView
    lateinit var nav_Email_Tv:TextView
    //스피너에 사용할 배열
    var spinnerArray = arrayListOf<ModelPickCategory>(ModelPickCategory(-2, "", "[주제] 전체보기"))

    lateinit var toolbar:Toolbar


    lateinit var lm: LinearLayoutManager
    private var fab_open: Animation? = null
    var fab_close: Animation? = null
    private var isFabOpen = false

    lateinit var droid:Drawable
    lateinit var droidTarget:Rect
    lateinit var sassyDesc:SpannableString

    lateinit var mDrawer:FlowingDrawer

    internal lateinit var preferences: SharedPreferences
    var tutorialCount:Int = 0

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSelectCategory()
        ing_Btn.setBackgroundColor(Color.rgb(19, 137, 255))
        presenterMain = PresenterMain().apply {
            view = this@MainActivity
            context = this@MainActivity
        }
        preferences = getSharedPreferences("USERSIGN", 0)


        setRecyclerView()
        //presenter 정의하고 아이템을 불러옴



        presenterMain.loadItems(arrayList, mAdapter, feedback_count)
        presenterMain.getSpinnerArray(spinnerArray)



        mDrawer = findViewById<View>(R.id.drawerlayout) as FlowingDrawer
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL)
        mDrawer.setOnDrawerStateChangeListener(object : ElasticDrawer.OnDrawerStateChangeListener {
            override fun onDrawerStateChange(oldState: Int, newState: Int) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED")
                }
            }

            override fun onDrawerSlide(openRatio: Float, offsetPixels: Int) {
                Log.i("MainActivity", "openRatio=$openRatio ,offsetPixels=$offsetPixels")
            }
        })
        setupToolbar()
        friends_List= findViewById<TextView>(R.id.friends_List) as TextView
        request_Feedback= findViewById<TextView>(R.id.request_Feedback) as TextView
        receive_Feedback= findViewById<TextView>(R.id.receive_Feedback) as TextView
        category_Setting= findViewById<TextView>(R.id.category_Setting) as TextView
        mypage= findViewById<TextView>(R.id.mypage) as TextView
        setting= findViewById<TextView>(R.id.setting) as TextView
        logout= findViewById<TextView>(R.id.logout) as TextView

        nav_Nickname_Tv= findViewById<TextView>(R.id.nav_Nickname_Tv) as TextView
        nav_Portrait_Iv= findViewById<ImageView>(R.id.nav_Portrait_Iv) as ImageView
        nav_Email_Tv= findViewById<TextView>(R.id.nav_Email_Tv) as TextView
        setNavView()
        friends_List.setOnClickListener{friends_List()
            mDrawer.closeMenu()}
        request_Feedback.setOnClickListener{request_Feedback()
            mDrawer.closeMenu()}
        receive_Feedback.setOnClickListener{receive_Feedback()
            mDrawer.closeMenu()}
        category_Setting.setOnClickListener{category_Setting()
            mDrawer.closeMenu()}
        mypage.setOnClickListener{mypage()
            mDrawer.closeMenu()}
        setting.setOnClickListener{setting()
            mDrawer.closeMenu()}
        logout.setOnClickListener{logout()
            mDrawer.closeMenu()}

        // '진행중'을 눌렀을 떄
        ing_Btn.setOnClickListener {
            ing_Btn.setBackgroundColor(Color.rgb(19, 137, 255))
            ing_Btn.setTextColor(Color.WHITE)
            ed_Btn.setBackgroundColor(Color.rgb(255, 255, 255))
            ed_Btn.setTextColor(Color.DKGRAY)
            when (feedbackIngEd) {
                0 -> {
                }//이미 0이면 그대로
                1 -> {
                    feedbackIngEd = 0
                    IngEdInit(feedbackIngEd)
                }
            }
        }
        // '진행완료'를 눌렀을 때
        ed_Btn.setOnClickListener {
            ing_Btn.setBackgroundColor(Color.rgb(255, 255, 255))
            ing_Btn.setTextColor(Color.DKGRAY)
            ed_Btn.setBackgroundColor(Color.rgb(19, 137, 255))
            ed_Btn.setTextColor(Color.WHITE)
            when (feedbackIngEd) {
                0 -> {
                    feedbackIngEd = 1
                    IngEdInit(feedbackIngEd)
                }
                1 -> {
                }
            }//이미 1이면 그대로
        }
        category_Spinner.setOnClickListener{
            feedbackIsFiltering = 1//이거 필터링임
            val intent = Intent(this, PickCategoryActivity::class.java)
            intent.putExtra("isMain", true)
            startActivityForResult(intent, 113)
        }

        //첫번째인지 판단하는것
        firstRunCheck()


    }
    protected fun setupToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.setTitle(" ")
        toolbar.setNavigationOnClickListener { mDrawer!!.toggleMenu() }


        // 액션바 타이틀 가운데 정렬
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.actionbar_title)
    }

    override fun showNothingText(type: Boolean){


        if(type){//리스트가 비었을때
            nothing_Text.visibility = View.VISIBLE
            Main_Recyclerview.visibility - View.GONE
        }else{//리스트가 비었을때
            nothing_Text.visibility = View.GONE
            Main_Recyclerview.visibility - View.VISIBLE
        }
    }


    //네비게이션 셋팅
    fun setNavView(){
        presenterMain.getMe()

    }






    //네비게이션 뷰셋팅
    override fun setNavData(nickname: String, email: String, portrait: String) {
        nav_Nickname_Tv.text = nickname
        nav_Email_Tv.text = email

       if(!portrait.equals("")){
           Picasso.get().load("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+portrait).into(nav_Portrait_Iv)
       }else{
           nav_Portrait_Iv.setImageResource(R.drawable.ic_default_profile)
       }

    }

    //진행중인거 완료된거 구별해서 아이템 불러오는 부분
    override fun IngEdInit(mfeedbackIngEd: Int) {
        when (feedbackIngEd) {
            0 -> {//진행중인거
                setSelectCategory()
                arrayList.clear()
                feedback_count = 0
                feedbackMyYour = 0
                presenterMain.loadItems(arrayList, mAdapter, feedback_count)
                setRecyclerView()
            }
            1 -> {//진행완료인거
                setSelectCategory()
                arrayList.clear()
                feedback_count = 0
                feedbackMyYour = 2//원래 내꺼면 0 아니면 1인데 내꺼중에 진행완료인거를 2로 둠
                presenterMain.loadCompleteItems(arrayList, mAdapter, feedback_count)
                setRecyclerView()
            }

        }
    }

    //피드백 마지막 아이디를 받아서 셋팅해주는 부분
    override fun setFeedbackCount(feedback_lastid: Int) {
        feedback_count = feedback_lastid
    }

    fun setRecyclerView() {

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        lm = LinearLayoutManager(this)
        Main_Recyclerview.layoutManager = lm
        mAdapter = AdapterMainFeedback(this, arrayList, presenterMain,  feedbackMyYour)
        Main_Recyclerview.adapter = mAdapter
        Main_Recyclerview.setHasFixedSize(true) //아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌
        Main_Recyclerview.clearOnScrollListeners()
        //무한 스크롤을 위해 리스너 추가함
        //요청받은건지 요청 한건지 구별함

        Main_Recyclerview.addOnScrollListener(InfiniteScrollListener({
            if (feedbackMyYour == 0) {//필터 아닐때
                if(feedbackIsFiltering == 0){
                    presenterMain.loadItems(arrayList, mAdapter, feedback_count)
                }else{//필터하는중일때
                    presenterMain.categoryFilter(selectedCategoryId,arrayList, mAdapter, feedback_count,feedbackIngEd )
                }
            } else if (feedbackMyYour == 1) {
                presenterMain.loadYourItems(arrayList, mAdapter, feedback_count)
            }
        }
            , lm)
        )//갱신
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            111 -> {    // 피드백 추가 후 돌아왔을 때
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        var user_uid:String = ""
                        if(data.hasExtra("user_uid")){
                            user_uid = data.getStringExtra("user_uid")
                        }
                        presenterMain.addItems(
                            arrayList,
                            data.getStringExtra("category_id").toInt(),
                            data.getStringExtra("date"),
                            data.getStringExtra("title"),
                            data.getStringExtra("color"),
                            user_uid,
                            mAdapter
                        )
                    }

                }
            }
            112 -> {    // 피드백 수정 후 돌아왔을 때
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        Log.e("return", data.getIntExtra("modify_id", -1).toString())
                        presenterMain.updateItems(
                            arrayList,
                            data.getIntExtra("modify_id", -1),
                            data.getIntExtra("category_id", -1),
                            data.getStringExtra("date"),
                            data.getStringExtra("title"),
                            data.getStringExtra("color"),
                            data.getStringExtra("user_uid"),
                            mAdapter
                        )
                    }

                }
            }
            113 -> {
                //주제 스피너 선택하고 돌아옴
                if(data == null){ }
                else{
                    Log.e("주제 선택후", data.getStringExtra("title")+data.getIntExtra("category_id", -1)+ data.getStringExtra("color") )
                    if(data.getStringExtra("color").equals("")){
                        main_Category_Color.visibility = View.GONE
                    }else{
                        main_Category_Color.visibility = View.VISIBLE
                        main_Category_Color.setBackgroundColor(Color.parseColor(data.getStringExtra("color")))
                    }
                    main_Category_Title.text = data.getStringExtra("title")
                    feedback_count = 0
                    selectedCategoryId = data.getIntExtra("id", -1)
                    arrayList.clear()

                    if(selectedCategoryId == -2){//전체보기를 선택한경우
                        feedbackIsFiltering = 0
                        if(feedbackIngEd == 0){//진행중인것일때
                            presenterMain.loadItems(arrayList, mAdapter, feedback_count)
                        }else{//진행완료일때
                            presenterMain.loadCompleteItems(arrayList, mAdapter, feedback_count)
                        }
                    }else{//그밖의 기타 필터링
                        presenterMain.categoryFilter(selectedCategoryId, arrayList, mAdapter, feedback_count,feedbackIngEd)
                    }
                    setRecyclerView()
                }


            }
        }
    }

    // 피드백 수정화면
    override fun modifyFeedbackActivity(id: Int, category_id: Int, date: String?, title: String) {
        val intent = Intent(this, CreateFeedbackActivity::class.java)
        Log.e("activity", id.toString())
        intent.putExtra("id", id)
        intent.putExtra("category_id", category_id)
        Log.e("modifyFeedbackActivity", category_id.toString())
        intent.putExtra("title", title)
        intent.putExtra("date", date)
        startActivityForResult(intent, 112)
    }

    //fab정의
    private fun toggleFab() {
        val intent = Intent(this, CreateFeedbackActivity::class.java)
        startActivityForResult(intent, 111)

    }
    //리사이클러뷰를 리프레시하는 용도
    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }


    //여기 아래로 네비게이션 옵션
    override fun onBackPressed() {
        if (mDrawer!!.isMenuVisible) {
            mDrawer!!.closeMenu()
        }else {
            // (현재 버튼 누른 시간-이전에 버튼 누른 시간) <=1.5초일 때 동작
            if(System.currentTimeMillis()-lastTimeBackPressed<=1500)
                finish()
            lastTimeBackPressed=System.currentTimeMillis()
            Toast.makeText(this,"한 번 더 누르면 종료됩니다",Toast.LENGTH_SHORT).show()
        }
    }

    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if(feedbackMyYour == 0 && feedbackIngEd == 0){
            menuInflater.inflate(R.menu.menu_main, menu)
        }else{

        }
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when (item.itemId) {
            R.id.create_Feedback_Button -> {
                return create_Feedback_Button()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun create_Feedback_Button():Boolean{
        val intent = Intent(this, CreateFeedbackActivity::class.java)
        startActivityForResult(intent, 111)
        return true
    }

    override fun onRestart() {
        super.onRestart()


        setNavView()
        arrayList.clear()
        feedback_count = 0
        when (feedbackMyYour) {
            0 -> {//내꺼진행중
                if (feedbackIsFiltering == 0) {
                    presenterMain.loadItems(arrayList, mAdapter, feedback_count)
                } else {
                    presenterMain.categoryFilter(
                        selectedCategoryId,
                        arrayList,
                        mAdapter,
                        feedback_count,
                        feedbackIngEd
                    )
                }
            }
            1 -> {//다른사람꺼
                presenterMain.loadYourItems(arrayList, mAdapter, feedback_count)
            }
            2 -> {//내꺼 진행완료
                if(feedbackIsFiltering == 0){//필터링 안하는거
                    presenterMain.loadCompleteItems(arrayList, mAdapter, feedback_count)
                }else{//필터링 하는거
                    presenterMain.categoryFilter(selectedCategoryId,arrayList, mAdapter, feedback_count,feedbackIngEd)

                }
            }
        }

        setRecyclerView()
    }
    fun setSelectCategory(){
        main_Category_Color.visibility = View.GONE
        main_Category_Title.text = "[주제] 전체보기"
    }

    //첫번째인지 체크
    fun firstRunCheck(){
        var isFirst:Boolean = preferences.getBoolean("firstMainActivity", true);
        if(isFirst){
            startTutorial()
        }
    }
    //튜토리얼 진행
    fun startTutorial(){
        when(tutorialCount){
            0 -> {var tframe = TutorialFrame("진행중인 피드백을 볼 수 있습니다!", "안녕하세요!", findViewById<View>(R.id.ing_Btn), this, { startTutorial()})
                tutorialCount++
                tframe.mTutorial()}
            1 -> {var tframe = TutorialFrame("진행이 완료된 피드백을 볼 수 있습니다!", "안녕하세요!", findViewById<View>(R.id.ed_Btn), this, { startTutorial()})
                tutorialCount++
                tframe.mTutorial()}
            2->{
                preferences.edit().putBoolean("firstMainActivity", false).apply()
            }
        }

    }

    fun request_Feedback() {
       if(feedbackMyYour == 0 && feedbackIngEd == 0){
       }else{
           setSelectCategory()
           arrayList.clear()
           feedbackIngEd = 0
           feedback_count = 0
           feedbackMyYour = 0//요청한거임
           presenterMain.loadItems(arrayList, mAdapter, feedback_count)
           ing_Btn.setBackgroundColor(Color.rgb(19, 137, 255))
           ed_Btn.setBackgroundColor(Color.rgb(255, 255, 255))
           setRecyclerView()
           ing_Case.visibility = View.VISIBLE
           category_Spinner.visibility = View.VISIBLE
       }
    }

    fun receive_Feedback() {
       if(feedbackMyYour == 1){
       }else{
           setSelectCategory()
           arrayList.clear()
           feedback_count = 0
           feedbackIngEd = 0
           feedbackMyYour = 1//요청받은거임
           presenterMain.loadYourItems(arrayList, mAdapter, feedback_count)
           setRecyclerView()
           ing_Case.visibility = View.GONE
           category_Spinner.visibility = View.GONE

       }
    }

    fun friends_List() {
        val intent = Intent(this, FriendsListActivity::class.java)
        startActivity(intent)

    }

    fun category_Setting() {
        val intent = Intent(this, CategorySettingActivity::class.java)
        startActivity(intent)
    }

    fun mypage() {
        val intent = Intent(this, MyPageActivity::class.java)
        startActivity(intent)
    }

    fun setting() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    fun feedback_Request_Alarm() {
        val intent = Intent(this, AlarmActivity::class.java)
        startActivity(intent)
    }

    fun logout(){
        var basicDialog: BasicDialog = BasicDialog("로그아웃 하시겠습니까?", this, { presenterMain.logout()
            finish()
            val editor = preferences.edit()
            editor.putString("autoLoginEmail", "")
            editor.putString("autoLoginPw", "")
            editor.commit()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }, {})
        basicDialog.makeDialog()
    }


}
