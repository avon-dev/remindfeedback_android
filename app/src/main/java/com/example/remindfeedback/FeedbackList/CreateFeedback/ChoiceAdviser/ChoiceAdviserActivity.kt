package com.example.remindfeedback.FeedbackList.CreateFeedback.ChoiceAdviser

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.FriendsList.AdapterFriendsList
import com.example.remindfeedback.FriendsList.ModelFriendsList
import com.example.remindfeedback.FriendsList.PresenterFriendsList
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_choice_adviser.*
import kotlinx.android.synthetic.main.activity_friends_list.*

class ChoiceAdviserActivity : AppCompatActivity() , ContractChoiceAdviser.View{


    internal lateinit var presenterChoiceAdviser: PresenterChoiceAdviser
    //리사이클러뷰에서 쓸 리스트와 어댑터 선언
    var arrayList = arrayListOf<ModelFriendsList>()
    lateinit var mAdapter: AdapterChoiceAdviser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_adviser)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("조언자 선택")


        //presenter 정의하고 아이템을 불러옴
        presenterChoiceAdviser = PresenterChoiceAdviser().apply {
            view = this@ChoiceAdviserActivity
            context = this@ChoiceAdviserActivity
        }
        mAdapter = AdapterChoiceAdviser(this, arrayList, presenterChoiceAdviser)

        presenterChoiceAdviser.loadItems(arrayList,mAdapter)


        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        choice_Adviser_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        choice_Adviser_Recyclerview.layoutManager = lm
        choice_Adviser_Recyclerview.setHasFixedSize(true) //아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

    }

    override fun returnData(user_uid:String){
        intent.putExtra("user_uid", user_uid)
        Log.e("choice activity", user_uid)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }
}
