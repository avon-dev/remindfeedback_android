package com.avon.remindfeedback.FriendsList.FriendsPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.avon.remindfeedback.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_friends_page.*

class FriendsPageActivity : AppCompatActivity(), ContractFriendsPage.View {

    lateinit var friendsProfileimage:String
    lateinit var friendsScript:String
    lateinit var friendsNickname:String
    lateinit var friendsUid:String
    var friendsType:Int = -2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_page)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("친구 페이지")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        var intent: Intent = intent
        friendsProfileimage = intent.getStringExtra("profileimage")
        friendsScript = intent.getStringExtra("script")
        friendsNickname = intent.getStringExtra("nickname")
        friendsUid = intent.getStringExtra("friend_uid")
        friendsType = intent.getIntExtra("type", -2)

        viewSetting()

    }


    fun viewSetting(){
        friendpage_Nickname_Tv.text = friendsNickname
        friendpage_Script_Tv.text = friendsScript
        friendpage_ImageView

        if(!friendsProfileimage.equals("")){
            Picasso.get().load("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+friendsProfileimage).into(friendpage_ImageView)
        }else{
            friendpage_ImageView.setImageResource(R.drawable.ic_default_profile)
        }

    }
}
