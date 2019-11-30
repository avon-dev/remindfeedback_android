package com.example.remindfeedback.FriendsList

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
            context = this@FriendsListActivity
        }
        presenterFriendsList.loadItems(arrayList)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            100 -> {
                when(resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        presenterFriendsList.addItems(data.getStringExtra("email"),mAdapter)
                    }
                    Activity.RESULT_CANCELED -> Toast.makeText(this@FriendsListActivity, "취소됨.", Toast.LENGTH_SHORT).show()

                    Activity.RESULT_FIRST_USER -> Toast.makeText(this@FriendsListActivity, "퍼스트 유저? 이건뭐임.", Toast.LENGTH_SHORT).show()
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
        when(item.itemId){
            R.id.add_Friends_Button -> { return add_Friends_Button() }
            else -> {return super.onOptionsItemSelected(item)}
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
