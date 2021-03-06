package com.avon.remindfeedback.FeedbackList.CreateFeedback.ChoiceAdviser

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.avon.remindfeedback.FriendsList.ModelFriendsList
import com.avon.remindfeedback.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdapterChoiceAdviser(
    val context: Context,
    val arrayList: ArrayList<ModelFriendsList>,
    val presenterChoiceAdviser: PresenterChoiceAdviser
) : RecyclerView.Adapter<AdapterChoiceAdviser.Holder>() {

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
        val friends_List_Profile_Image =
            itemView.findViewById<ImageView>(R.id.friends_List_Profile_Image)
        val friends_List_Name = itemView.findViewById<TextView>(R.id.friends_List_Name)
        val friend_Accept_Button = itemView.findViewById<Button>(R.id.friend_Accept_Button)
        val friend_Reject_Button = itemView.findViewById<Button>(R.id.friend_Reject_Button)


        fun bind(friends_list: ModelFriendsList, context: Context) {

            //상대이름, 피드백제목, 피드백 작성일 등 정의해줌
            friends_List_Name.text = friends_list.friendsName
            friends_List_script.text = friends_list.friendsScript
            Log.e("viewinit", friends_list.viewinit.toString())

            friend_Accept_Button.visibility = View.GONE
            friend_Reject_Button.visibility = View.GONE


            if (friends_list.friendsProfileImage.equals("")) {
                friends_List_Profile_Image.setImageResource(R.drawable.ic_default_profile)
            } else {
                Picasso.get().load("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/" + friends_list.friendsProfileImage).into(friends_List_Profile_Image)
            }


            itemView.setOnClickListener {
                presenterChoiceAdviser.returnData(friends_list.friend_uid, friends_list.friendsProfileImage, friends_list.friendsScript, friends_list.friendsName)
            }


        }
    }


}