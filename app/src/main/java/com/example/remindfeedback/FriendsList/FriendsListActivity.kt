package com.example.remindfeedback.FriendsList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.FeedbackList.AdapterMainFeedback
import com.example.remindfeedback.FeedbackList.MainActivity
import com.example.remindfeedback.FeedbackList.ModelFeedback
import com.example.remindfeedback.FeedbackList.PresenterMain
import com.example.remindfeedback.FriendsList.FindFriends.FindFriendsActivity
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class FriendsListActivity : AppCompatActivity(), ContractFriendsList.View {

    private val TAG = "FriendsListActivity"
    internal lateinit var presenterFriendsList: PresenterFriendsList


    //리사이클러뷰에서 쓸 리스트와 어댑터 선언
    var arrayList = arrayListOf<ModelFriendsList>(

        ModelFriendsList("김가희", "친구의 상메내용 1", "dummy"),
        ModelFriendsList("김나희", "친구의 상메내용 2", "dummy"),
        ModelFriendsList("김다희", "친구의 상메내용 3", "dummy"),
        ModelFriendsList("김라희", "친구의 상메내용 4", "dummy"),
        ModelFriendsList("김마희", "친구의 상메내용 5", "dummy"),
        ModelFriendsList("김바희", "친구의 상메내용 6", "dummy"),
        ModelFriendsList("김사희", "친구의 상메내용 7", "dummy"),
        ModelFriendsList("김아희", "친구의 상메내용 8", "dummy"),
        ModelFriendsList("김자희", "친구의 상메내용 9", "dummy"),
        ModelFriendsList("김차희", "친구의 상메내용 10", "dummy"),
        ModelFriendsList("김카희", "친구의 상메내용 11", "dummy"),
        ModelFriendsList("김타희", "친구의 상메내용 12", "dummy"),
        ModelFriendsList("김파희", "친구의 상메내용 13", "dummy"),
        ModelFriendsList("김하희", "친구의 상메내용 14", "dummy")




    )
    val mAdapter = AdapterFriendsList(this, arrayList)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("친구 목록")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        freinds_List_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        freinds_List_Recyclerview.layoutManager = lm
        freinds_List_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌


        //presenter 정의하고 아이템을 불러옴
        presenterFriendsList = PresenterFriendsList().apply {
            view = this@FriendsListActivity
        }
        presenterFriendsList.loadItems(arrayList)

        //친구추가 버튼 눌렀을때
        add_Friends_Button.setOnClickListener {
            val intent = Intent(this, FindFriendsActivity::class.java)
            startActivity(intent)
            //finish()

        }

    }


    override fun refresh() {
        mAdapter.notifyDataSetChanged()

    }

}
