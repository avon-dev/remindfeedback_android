package com.example.remindfeedback.FriendsList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.FriendsList.FindFriends.FindFriendsActivity
import com.example.remindfeedback.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_friends_list.*

class FriendsListActivity : AppCompatActivity(), ContractFriendsList.View {

    private val TAG = "FriendsListActivity"
    internal lateinit var presenterFriendsList: PresenterFriendsList

    //리사이클러뷰에서 쓸 리스트와 어댑터 선언
    var arrayList = arrayListOf<ModelFriendsList>()
    lateinit var mAdapter:AdapterFriendsList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("친구 목록")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)


        //presenter 정의하고 아이템을 불러옴
        presenterFriendsList = PresenterFriendsList().apply {
            view = this@FriendsListActivity
            context = this@FriendsListActivity
        }
        mAdapter = AdapterFriendsList(this, arrayList,presenterFriendsList)

        val tabs = findViewById<View>(R.id.friends_Tab_Layout) as TabLayout

        var intent:Intent = intent
        if(intent.hasExtra("blocked")){//설정의 차단친구관리에서 넘어오는 인텐트
            presenterFriendsList.loadBlockedFriends(arrayList,mAdapter)
            tabs.visibility = View.GONE
            ab.setTitle("차단한 친구 목록")
        }else{
            presenterFriendsList.loadItems(arrayList,mAdapter)
        }


        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        freinds_List_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        freinds_List_Recyclerview.layoutManager = lm
        freinds_List_Recyclerview.setHasFixedSize(true) //아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌



//여기부터 탭바 코드
        //3탭기능 구성
        tabs.addTab(tabs.newTab().setText("친구목록"))
        tabs.addTab(tabs.newTab().setText("받은친구요청"))
        tabs.addTab(tabs.newTab().setText("보낸친구요청"))
        //탭버튼을 클릭했을 때 프레그먼트 동작
        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //선택된 탭 번호 반환
                val position = tab.position
                if (position == 0) {
                    Toast.makeText(this@FriendsListActivity, "친구목록", Toast.LENGTH_SHORT).show()
                    presenterFriendsList.loadItems(arrayList,mAdapter)
                } else if (position == 1) {
                    Toast.makeText(this@FriendsListActivity, "받은친구요청", Toast.LENGTH_SHORT).show()
                    presenterFriendsList.receivedFriendRequests(arrayList,mAdapter)
                } else if (position == 2) {
                    Toast.makeText(this@FriendsListActivity, "보낸친구요청", Toast.LENGTH_SHORT).show()
                    presenterFriendsList.requestedFriendsRequests(arrayList,mAdapter)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
        //여기까지 탭바 코드
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            100 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        presenterFriendsList.addItems(data.getStringExtra("email"), mAdapter)
                    }
                    Activity.RESULT_CANCELED -> Toast.makeText(this@FriendsListActivity, "취소됨.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.friends_list_menu, menu)
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when (item.itemId) {
            R.id.add_Friends_Button -> {
                return add_Friends_Button()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    //찾기버튼 눌렀을때
    fun add_Friends_Button(): Boolean {
        val intent = Intent(this, FindFriendsActivity::class.java)
        startActivityForResult(intent, 100)
        return true
    }

    override fun refresh() {
        mAdapter.notifyDataSetChanged()

    }

}
