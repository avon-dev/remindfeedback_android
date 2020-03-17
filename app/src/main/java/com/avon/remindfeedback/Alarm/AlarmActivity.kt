package com.avon.remindfeedback.Alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.avon.remindfeedback.R
import com.google.android.material.tabs.TabLayout
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_alarm.*


class AlarmActivity : AppCompatActivity(), ContractAlarm.View {

    private val TAG = "AlarmActivity"
    internal lateinit var presenterAlarm: PresenterAlarm
    var arrayList = arrayListOf<ModelAlarm>(
        ModelAlarm("dummy", "첫번째 알람", "10분", "공지", true),
        ModelAlarm("dummy", "두번째 알람", "27분", "피드백", true),
        ModelAlarm("dummy", "세번째 알람", "35분", "공지", false),
        ModelAlarm("dummy", "네번째 알람", "1일", "피드백", true),
        ModelAlarm("dummy", "다섯번째 알람", "1일", "공지", false),
        ModelAlarm("dummy", "여섯번째 알람", "1일", "공지", true),
        ModelAlarm("dummy", "일곱번째 알람", "7일", "피드백", false),
        ModelAlarm("dummy", "여덟번째 알람", "8일", "공지", true),
        ModelAlarm("dummy", "아홉번째 알람", "1주", "피드백", false),
        ModelAlarm("dummy", "열번째 알람", "2주", "공지", true),
        ModelAlarm("dummy", "열한번째 알람", "27주", "피드백", true),
        ModelAlarm("dummy", "열두번째 알람", "52주", "공지", false)
    )
    val mAdapter = AdapterAlarm(this, arrayList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("알람")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        alarm_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        alarm_Recyclerview.layoutManager = lm
        alarm_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        presenterAlarm = PresenterAlarm().apply {
            view = this@AlarmActivity
        }
        presenterAlarm.loadItems(arrayList)


        //여기부터 탭바 코드
        //3탭기능 구성
        val tabs = findViewById<View>(R.id.tab_layout) as TabLayout
        tabs.addTab(tabs.newTab().setText("전체"))
        tabs.addTab(tabs.newTab().setText("공지"))
        tabs.addTab(tabs.newTab().setText("피드백"))
        //탭버튼을 클릭했을 때 프레그먼트 동작
        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                //선택된 탭 번호 반환
                val position = tab.position

                if (position == 0) {
                    Toast.makeText(this@AlarmActivity, "전체.", Toast.LENGTH_SHORT).show()
                    presenterAlarm.loadItems(arrayList)
                } else if (position == 1) {
                    Toast.makeText(this@AlarmActivity, "공지.", Toast.LENGTH_SHORT).show()
                    presenterAlarm.loadAlarm(arrayList)
                } else if (position == 2) {
                    Toast.makeText(this@AlarmActivity, "피드백.", Toast.LENGTH_SHORT).show()
                    presenterAlarm.loadFeedback(arrayList)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
        //여기까지 탭바 코드
    }


    override fun refresh() {

    }

}
