package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.R
import java.util.ArrayList

class AdapterPost(val context: Context, val arrayList: ArrayList<ModelPost>) :   RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun addItem(item: ModelPost) {//아이템 추가
        if (arrayList != null) {
            arrayList.add(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        //getItemViewType 에서 뷰타입 1을 리턴받았다면 내채팅레이아웃을 받은 Holder를 리턴
        if(viewType == 1){
            view = LayoutInflater.from(context).inflate(R.layout.item_post_comment, parent, false)
            return Holder(view)
        }
        //getItemViewType 에서 뷰타입 2을 리턴받았다면 상대채팅레이아웃을 받은 Holder2를 리턴
        else{
            view = LayoutInflater.from(context).inflate(R.layout.item_post_video, parent, false)
            return Holder2(view)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        //onCreateViewHolder에서 리턴받은 뷰홀더가 Holder라면 내채팅, item_my_chat의 뷰들을 초기화 해줌
        if (viewHolder is Holder) {
            (viewHolder as Holder).post_Comment_Name.setText(arrayList.get(i).name)
            (viewHolder as Holder).post_Comment_Script.setText(arrayList.get(i).script)
            (viewHolder as Holder).post_Comment_Date.setText(arrayList.get(i).date)

            //이미지는 보류
            //(viewHolder as Holder).post_Comment_Profile_Image.setImage(arrayList.get(i).profileImage)

        }
        //onCreateViewHolder에서 리턴받은 뷰홀더가 Holder2라면 상대의 채팅, item_your_chat의 뷰들을 초기화 해줌
        else if(viewHolder is Holder2) {

            //(viewHolder as Holder2).post_Video_Tv.setText(arrayList.get(i).AAAAAAAAAAAAAAA)
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val post_Comment_Name :TextView= itemView.findViewById(R.id.post_Comment_Name)
        val post_Comment_Script :TextView= itemView.findViewById(R.id.post_Comment_Script)
        val post_Comment_Date :TextView= itemView.findViewById(R.id.post_Comment_Date)
        val post_Comment_Profile_Image:ImageView = itemView.findViewById(R.id.post_Comment_Profile_Image)
    }

    inner class Holder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val post_Video_Tv:TextView = itemView.findViewById(R.id.post_Video_Tv)


    }

    override fun getItemViewType(position: Int): Int {//여기서 뷰타입을 1, 2로 바꿔서 지정해줘야 내채팅 너채팅을 바꾸면서 쌓을 수 있음

        //내 아이디와 arraylist의 name이 같다면 내꺼 아니면 상대꺼
        return if (arrayList.get(position).type == 1) {
            1
        } else if (arrayList.get(position).type == 2) {
            2
        } else {
            3
        }
    }
}