package com.example.remindfeedback.FeedbackList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.Display
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.Alarm.AlarmActivity
import com.example.remindfeedback.CategorySetting.CategorySettingActivity
import com.example.remindfeedback.FeedbackList.CreateFeedback.CreateFeedbackActivity
import com.example.remindfeedback.FeedbackList.CreateFeedback.PickCategory.ModelPickCategory
import com.example.remindfeedback.FeedbackList.CreateFeedback.PickCategory.PickCategoryActivity
import com.example.remindfeedback.FeedbackList.FeedbackDetail.FeedbackDetailActivity
import com.example.remindfeedback.FriendsList.FriendsListActivity
import com.example.remindfeedback.MyPage.MyPageActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.Setting.SettingActivity
import com.example.remindfeedback.etcProcess.BasicDialog
import com.example.remindfeedback.etcProcess.InfiniteScrollListener
import com.example.remindfeedback.etcProcess.MyProgress
import com.example.remindfeedback.etcProcess.URLtoBitmapTask
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.getkeepsafe.taptargetview.TapTargetView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import java.net.URL

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ContractMain.View, View.OnClickListener {

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

    lateinit var nav_Nickname_Tv:TextView
    lateinit var nav_Portrait_Iv:ImageView
    lateinit var nav_Email_Tv:TextView
    //스피너에 사용할 배열
    var spinnerArray = arrayListOf<ModelPickCategory>(ModelPickCategory(-2, "", "전체보기"))

    lateinit var toolbar:Toolbar

    lateinit var fab_main: FloatingActionButton
    lateinit var fab_sub1: FloatingActionButton
    lateinit var fab_sub2: FloatingActionButton
    lateinit var lm: LinearLayoutManager
    private var fab_open: Animation? = null
    var fab_close: Animation? = null
    private var isFabOpen = false

    lateinit var droid:Drawable
    lateinit var droidTarget:Rect
    lateinit var sassyDesc:SpannableString
    var tutorialCount:Int = 0

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var display:Display = windowManager.defaultDisplay

        setSelectCategory()
        ing_Btn.setBackgroundColor(Color.rgb(19, 137, 255))
        presenterMain = PresenterMain().apply {
            view = this@MainActivity
            context = this@MainActivity
        }


        setRecyclerView()
        //presenter 정의하고 아이템을 불러옴

        //튜토리얼을 위한 세팅
        tutorialSet(display)


        presenterMain.loadItems(arrayList, mAdapter, feedback_count)
        presenterMain.getSpinnerArray(spinnerArray)

        //여기부터 fab코드
        fab_open = AnimationUtils.loadAnimation(this, R.animator.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.animator.fab_close)
        fab_main = findViewById(R.id.fab_main)
//        fab_sub1 = findViewById(R.id.fab_sub1)
//        fab_sub2 = findViewById(R.id.fab_sub2)
        fab_main.setOnClickListener(this);
//        fab_sub1.setOnClickListener(this);
//        fab_sub2.setOnClickListener(this);



        ing_Btn.setOnClickListener {
            ing_Btn.setBackgroundColor(Color.rgb(19, 137, 255))
            ed_Btn.setBackgroundColor(Color.rgb(255, 255, 255))
            when (feedbackIngEd) {
                0 -> {
                }//이미 0이면 그대로
                1 -> {
                    feedbackIngEd = 0
                    IngEdInit(feedbackIngEd)
                }
            }
        }
        ed_Btn.setOnClickListener {
            ing_Btn.setBackgroundColor(Color.rgb(255, 255, 255))
            ed_Btn.setBackgroundColor(Color.rgb(19, 137, 255))

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






        //mTutorial("첫번째 피드백을 등록해보세요!", "안녕하세요!", findViewById<View>(R.id.fab_main))

    }

    fun tutorialSet(display: Display){
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_right_black)
        setNavView()
        //액션바 설정
        ab = this!!.supportActionBar!!
        ab.setTitle("Remind Feedback")

        // 드로어블을로드하고 여기에 탭 대상을 표시 할 위치를 만듭니다
        // 이 시점에서 너비와 높이를 얻으려면 디스플레이가 필요합니다
        //display = windowManager.defaultDisplay
        // Load our little droid guy
        droid = ContextCompat.getDrawable(this, R.drawable.ic_right_black)!!
        // Tell our droid buddy where we want him to appear
        droidTarget = Rect(0, 0, droid!!.intrinsicWidth * 2, droid.intrinsicHeight * 2)
        // Using deprecated methods makes you look way cool
        droidTarget.offset(display.width / 2, display.height / 2)

        sassyDesc = SpannableString("편의를 도와주는 네비게이션입니다.")
        sassyDesc.setSpan(
            StyleSpan(Typeface.ITALIC),
            sassyDesc.length - "sometimes".length,
            sassyDesc.length,
            0
        )
    }

    //네비게이션 셋팅
    fun setNavView(){
        //드로어 네비게이션 관련 코드
        setSupportActionBar(toolbar)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        val nav_header_view = navView.getHeaderView(0)

        nav_Nickname_Tv = nav_header_view.findViewById<View>(R.id.nav_Nickname_Tv) as TextView
        nav_Portrait_Iv = nav_header_view.findViewById<View>(R.id.nav_Portrait_Iv) as ImageView
        nav_Email_Tv = nav_header_view.findViewById<View>(R.id.nav_Email_Tv) as TextView

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
                    Activity.RESULT_CANCELED -> Toast.makeText(
                        this@MainActivity,
                        "취소됨.",
                        Toast.LENGTH_SHORT
                    ).show()
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
                    Activity.RESULT_CANCELED -> Toast.makeText(
                        this@MainActivity,
                        "취소됨.",
                        Toast.LENGTH_SHORT
                    ).show()
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

    //fab을 위해서 onclick 상속받음
    override fun onClick(v: View?) {
        when (v!!.getId()) {
            R.id.fab_main ->
                toggleFab()
            /*
            R.id.fab_sub1 -> {
                toggleFab()
                Toast.makeText(this, "1번!!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CreateFeedbackActivity::class.java)
                startActivityForResult(intent, 111)

            }
            R.id.fab_sub2 -> {
                toggleFab()
                Toast.makeText(this, "2번!!", Toast.LENGTH_SHORT).show()
            }
            */

        }
    }

    //fab정의
    private fun toggleFab() {
        //원래 fab_sub2에 있던 부분인데 그냥 메인누르면 실행되게 함
        val intent = Intent(this, CreateFeedbackActivity::class.java)
        startActivityForResult(intent, 111)

        /*
        if (isFabOpen) {
            fab_main.setImageResource(R.drawable.ic_add_black)
            fab_sub1.startAnimation(fab_close)
            fab_sub2.startAnimation(fab_close)
            fab_sub1.setClickable(false)
            fab_sub2.setClickable(false)
            isFabOpen = false
        } else {
            fab_main.setImageResource(R.drawable.ic_add_black)
            fab_sub1.startAnimation(fab_open)
            fab_sub2.startAnimation(fab_open)
            fab_sub1.setClickable(true)
            fab_sub2.setClickable(true)
            isFabOpen = true
        }
        */
    }
    //리사이클러뷰를 리프레시하는 용도
    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }


    //여기 아래로 네비게이션 옵션
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            // (현재 버튼 누른 시간-이전에 버튼 누른 시간) <=1.5초일 때 동작
            if(System.currentTimeMillis()-lastTimeBackPressed<=1500)
                finish()
            lastTimeBackPressed=System.currentTimeMillis()
            Toast.makeText(this,"한 번 더 누르면 종료됩니다",Toast.LENGTH_SHORT).show()
        }
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
        main_Category_Title.text = "전체보기"
    }

    fun mTutorial(contents:String, title:String, view:View){
        // You don't always need a sequence, and for that there's a single time tap target
        val spannedDesc = SpannableString(contents)
        /*
        spannedDesc.setSpan(
            UnderlineSpan(),
            spannedDesc.length - "TapTargetView".length,
            spannedDesc.length,
            0
        )

         */
        TapTargetView.showFor(this,
            TapTarget.forView(view, title, spannedDesc)
                .cancelable(false)
                .drawShadow(true)
                .titleTextDimen(R.dimen.fab_margin)
                .tintTarget(false),
            object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView) {
                    super.onTargetClick(view)
                    when(tutorialCount){
                        0 -> {mTutorial("진행중인 피드백을 볼 수 있습니다!", "안녕하세요!", findViewById<View>(R.id.ing_Btn))
                            tutorialCount++}
                        1 -> {mTutorial("진행이 완료된 피드백을 볼 수 있습니다!", "안녕하세요!", findViewById<View>(R.id.ed_Btn))
                            tutorialCount++}
                        2 -> {drawerTutorial()}

                    }
                }

                override fun onOuterCircleClick(view: TapTargetView?) {
                    super.onOuterCircleClick(view)
                    Toast.makeText(
                        view!!.context,
                        "You clicked the outer circle!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onTargetDismissed(view: TapTargetView?, userInitiated: Boolean) {
                    Log.d("TapTargetViewSample", "You dismissed me :(")
                }
            })

    }

    fun drawerTutorial(){
        // We have a sequence of targets, so lets build it!
        val sequence = TapTargetSequence(this)
            .targets(
                // 이 탭 대상은 뒤로 버튼을 대상으로합니다. 포함 도구 모음을 전달하면됩니다.
                TapTarget.forToolbarNavigationIcon(
                    toolbar,
                    "안녕하세요!",
                    sassyDesc
                ).id(1)
            )
            .listener(object : TapTargetSequence.Listener {
                // This listener will tell us when interesting(tm) events happen in regards
                // to the sequence
                override fun onSequenceFinish() {
                   Toast.makeText(this@MainActivity, "튜토리얼을 완료했습니다.", Toast.LENGTH_LONG).show()
                }

                override fun onSequenceStep(lastTarget: TapTarget, targetClicked: Boolean) {
                    Log.d("TapTargetView", "Clicked on " + lastTarget.id())
                }

                override fun onSequenceCanceled(lastTarget: TapTarget) {
                    /*
                    val dialog = AlertDialog.Builder(this@MainActivity)
                        .setTitle("Uh oh")
                        .setMessage("You canceled the sequence")
                        .setPositiveButton("Oops", null).show()
                    TapTargetView.showFor(dialog,
                        TapTarget.forView(
                            dialog.getButton(DialogInterface.BUTTON_POSITIVE),
                            "Uh oh!",
                            "You canceled the sequence at step " + lastTarget.id()
                        )
                            .cancelable(false)
                            .tintTarget(false), object : TapTargetView.Listener() {
                            override fun onTargetClick(view: TapTargetView) {
                                super.onTargetClick(view)
                                dialog.dismiss()
                            }
                        })
                    */
                }
            })

        sequence.start()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            //필요없을거같이서 일단 지움
            //R.id.alarm_Alarm -> true
            //R.id.search_Button -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //네비게이션바에서 아이템 클릭시
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.request_Feedback -> request_Feedback()
            R.id.receive_Feedback -> receive_Feedback()
            R.id.friends_List -> friends_List()
            R.id.category_Setting -> category_Setting()
            R.id.mypage -> mypage()
            R.id.setting -> setting()
            R.id.logout -> logout()
            //R.id.feedback_Request_Alarm -> feedback_Request_Alarm() //알림은 알파버전에서 제외함

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
           //ing_Linear.visibility = View.VISIBLE
           ing_Case.visibility = View.VISIBLE
           fab_main.visibility = View.VISIBLE
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

           //ing_Linear.visibility = View.GONE
           ing_Case.visibility = View.GONE
           fab_main.visibility = View.GONE
           category_Spinner.visibility = View.GONE
//           fab_sub1.startAnimation(fab_close)
//           fab_sub2.startAnimation(fab_close)
//           fab_sub1.setClickable(false)
//           fab_sub2.setClickable(false)
//           isFabOpen = false
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
            finish()}, {})
        basicDialog.makeDialog()
    }


}
