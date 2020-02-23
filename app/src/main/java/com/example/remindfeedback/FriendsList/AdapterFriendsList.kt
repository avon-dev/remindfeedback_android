package com.example.remindfeedback.FriendsList

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.FeedbackList.AdapterMainFeedback
import com.example.remindfeedback.FeedbackList.ModelFeedback
import com.example.remindfeedback.FriendsList.FriendsPage.FriendsPageActivity
import com.example.remindfeedback.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_page.*
import java.net.URL
import java.util.ArrayList

/*
* viewinit
* 0은 내친구들
* 1은 받은 친구요청
* 2는 보낸친구요청
* */

class AdapterFriendsList(val context: Context, val arrayList: ArrayList<ModelFriendsList>, val presenterFriendsList: PresenterFriendsList) :   RecyclerView.Adapter<AdapterFriendsList.Holder>() {


    fun addItem(item: ModelFriendsList) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(item)
        }
    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_friends_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position], context)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //모델의 변수들 정의하는부분
        val friends_List_script = itemView.findViewById<TextView>(R.id.friends_List_script)
        val friends_List_Profile_Image = itemView.findViewById<ImageView>(R.id.friends_List_Profile_Image)
        val friends_List_Name = itemView.findViewById<TextView>(R.id.friends_List_Name)
        val friend_Accept_Button = itemView.findViewById<Button>(R.id.friend_Accept_Button)
        val friend_Reject_Button = itemView.findViewById<Button>(R.id.friend_Reject_Button)



        fun bind (friends_list: ModelFriendsList, context: Context) {

            //상대이름, 피드백제목, 피드백 작성일 등 정의해줌
            friends_List_Name.text = friends_list.friendsName
            friends_List_script.text = friends_list.friendsScript
            Log.e("viewinit", friends_list.viewinit.toString())
            if(friends_list.viewinit == 0 || friends_list.viewinit == 2 || friends_list.viewinit == 3){//내가 요청했거나 이미 친구이거나 차단된 친구이면 수락 거절버튼 안보이게 함
                friend_Accept_Button.visibility = View.GONE
                friend_Reject_Button.visibility = View.GONE
            }else{
                friend_Accept_Button.visibility = View.VISIBLE
                friend_Reject_Button.visibility = View.VISIBLE
            }

            if(!friends_list.friendsProfileImage.equals("")){
                Picasso.get().load("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+friends_list.friendsProfileImage).into(friends_List_Profile_Image)

            }else{
                friends_List_Profile_Image.setImageResource(R.drawable.ic_default_profile)
            }


            //친구목록의 아이템을 선택하면 친구페이지로 가게됨
            itemView.setOnClickListener {
                val intent = Intent(context, FriendsPageActivity::class.java)
                intent.putExtra("nickname", friends_list.friendsName)
                intent.putExtra("profileimage", friends_list.friendsProfileImage)
                intent.putExtra("script", friends_list.friendsScript)
                intent.putExtra("friend_uid", friends_list.friend_uid)
                intent.putExtra("type", friends_list.type)
                Toast.makeText(context, friends_list.friendsName+"의 친구페이지", Toast.LENGTH_SHORT).show()
                context.startActivity(intent)
            }

            itemView.setOnLongClickListener {
                var dialogInterface: DialogInterface? = null
                val dialog = AlertDialog.Builder(context)
                val edialog: LayoutInflater = LayoutInflater.from(context)
                val mView: View = edialog.inflate(R.layout.dialog_friend_longclick, null)

                val block_Tv: TextView = mView.findViewById(R.id.block_Tv)

                if(friends_list.viewinit == 3){
                    block_Tv.setText("차단 해제")
                }
                block_Tv.setOnClickListener {
                    if(friends_list.viewinit == 3){//차단해제
                        presenterFriendsList.unBlockRequest(arrayList,friends_list.friend_uid,friends_list.friend_id, friends_list.friend_uid, this@AdapterFriendsList)
                    }else{//차단
                        presenterFriendsList.blockRequest(arrayList,friends_list.friend_uid,friends_list.friend_id, friends_list.friend_uid, this@AdapterFriendsList )
                    }
                    dialogInterface!!.dismiss()
                }

                dialog.setView(mView)
                dialog.create()
                dialogInterface = dialog.show()

                return@setOnLongClickListener true
            }

            //수락버튼 눌럿을때
            friend_Accept_Button.setOnClickListener{
                presenterFriendsList.acceptRequest(arrayList,friends_list.friend_uid, this@AdapterFriendsList)
            }
            //거절버튼 눌럿을때
            friend_Reject_Button.setOnClickListener{
                presenterFriendsList.rejectRequest(arrayList,friends_list.friend_uid,friends_list.friend_id, friends_list.friend_uid, this@AdapterFriendsList)
            }
        }
    }



}