package com.example.remindfeedback.FeedbackList

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.Alarm.AlarmActivity
import com.example.remindfeedback.CategorySetting.CategorySettingActivity
import com.example.remindfeedback.CreateFeedback.CreateFeedbackActivity
import com.example.remindfeedback.FriendsList.FriendsListActivity
import com.example.remindfeedback.MyPage.MyPageActivity
import com.example.remindfeedback.CreateCategory.CreateCategoryActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import com.example.remindfeedback.Setting.SettingActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_login.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ContractMain.View,
    View.OnClickListener {


    private val TAG = "MainActivity"
    internal lateinit var presenterMain: PresenterMain


    //리사이클러뷰에서 쓸 리스트와 어댑터 선언
    companion object {  // kotlin에는 static없음
        var arrayList = arrayListOf<ModelFeedback>(
            //ModelFeedback("김철수", "blue", "첫번째 피드백 제목", "dummy", "2019.10.28 월", false)
        )
    }

    val mAdapter = AdapterMainFeedback(this, arrayList)


    //스피너에 사용할 배열
    var spinnerArray = arrayListOf<String>(
        "<주제> 전체보기", "외국어", "생활습관", "과제", "업무", "시험", "자기계발"
    )
    private val mContext: Context = this


    lateinit var fab_main: FloatingActionButton
    lateinit var fab_sub1: FloatingActionButton
    lateinit var fab_sub2: FloatingActionButton

    private var fab_open: Animation? = null
    var fab_close: Animation? = null

    private var isFabOpen = false

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        ing_Btn.setBackgroundColor(Color.rgb(19, 137, 255))


        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        Main_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        Main_Recyclerview.layoutManager = lm
//        Main_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌


        //presenter 정의하고 아이템을 불러옴
        presenterMain = PresenterMain().apply {
            view = this@MainActivity
        }
        presenterMain.loadItems(arrayList)


        //여기서부터는 스피너 관련코드
        var arrayAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerArray
        )

        category_Spinner

        category_Spinner.setAdapter(arrayAdapter)
        category_Spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                Toast.makeText(
                    applicationContext, spinnerArray[i].toString() + "가 선택되었습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })
        //여기까지 spinner관련코드


        //여기부터 fab코드
        fab_open = AnimationUtils.loadAnimation(mContext, R.animator.fab_open)
        fab_close = AnimationUtils.loadAnimation(mContext, R.animator.fab_close)
        fab_main = findViewById(R.id.fab_main)
        fab_sub1 = findViewById(R.id.fab_sub1)
        fab_sub2 = findViewById(R.id.fab_sub2)
        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);



        ing_Btn.setOnClickListener {
            ing_Btn.setBackgroundColor(Color.rgb(19, 137, 255))
            ed_Btn.setBackgroundColor(Color.rgb(255, 255, 255))
        }
        ed_Btn.setOnClickListener {
            ing_Btn.setBackgroundColor(Color.rgb(255, 255, 255))
            ed_Btn.setBackgroundColor(Color.rgb(19, 137, 255))
        }


    }


    override fun onStart() {
        super.onStart()
        mAdapter.notifyDataSetChanged()

        Log.e("onStart", mAdapter.arrayList.size.toString())

    }

    //fab을 위해서 onclick 상속받음
    override fun onClick(v: View?) {
        when (v!!.getId()) {
            R.id.fab_main ->
                toggleFab()
            R.id.fab_sub1 -> {
                toggleFab()
                Toast.makeText(this, "1번!!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CreateFeedbackActivity::class.java)
                startActivity(intent)
            }
            R.id.fab_sub2 -> {
                toggleFab()
                Toast.makeText(this, "2번!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //fab정의
    private fun toggleFab() {
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
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.alarm_Alarm -> true
            R.id.search_Button -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //네비게이션바에서 아이템 클릭시
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.e(TAG, "onNavigationItemSelected")
        when (item.itemId) {
            R.id.request_Feedback -> request_Feedback()
            R.id.receive_Feedback -> receive_Feedback()
            R.id.friends_List -> friends_List()
            R.id.category_Setting -> category_Setting()
            R.id.mypage -> mypage()
            R.id.setting -> setting()
            R.id.feedback_Request_Alarm -> feedback_Request_Alarm()

        }

        //여기 선택된거 색 바꿔주는 부분임 일단 주석처리
        //item.isChecked = true
        //item.title
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    fun request_Feedback() {
        Toast.makeText(this@MainActivity, "request_Feedback.", Toast.LENGTH_SHORT).show()
        ing_Case.visibility = View.VISIBLE
        fab_main.visibility = View.VISIBLE


    }

    fun receive_Feedback() {
        Toast.makeText(this@MainActivity, "receive_Feedback.", Toast.LENGTH_SHORT).show()
        ing_Case.visibility = View.INVISIBLE
        fab_main.visibility = View.INVISIBLE
        fab_sub1.startAnimation(fab_close)
        fab_sub2.startAnimation(fab_close)
        fab_sub1.setClickable(false)
        fab_sub2.setClickable(false)
        isFabOpen = false
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

}
