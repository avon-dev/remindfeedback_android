package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
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
        //뷰타입 구분함
        if(viewType == 1){
            view = LayoutInflater.from(context).inflate(R.layout.item_post_comment, parent, false)
            return Holder(view)
        }
        else if(viewType == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.item_post_video, parent, false)
            return Holder2(view)
        }else if(viewType == 3) {
            view = LayoutInflater.from(context).inflate(R.layout.item_post_voice, parent, false)
            return Holder3(view)
        }else if(viewType == 4) {
            view = LayoutInflater.from(context).inflate(R.layout.item_post_text, parent, false)
            return Holder4(view)
        }else if(viewType == 5) {
            view = LayoutInflater.from(context).inflate(R.layout.item_post_photo, parent, false)
            return Holder5(view)
        }else {
            //일단 뷰타입이 1-5 중에 없을때 5번째 뷰홀더 리턴하도록함
            view = LayoutInflater.from(context).inflate(R.layout.item_post_photo, parent, false)
            return Holder5(view)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (viewHolder is Holder) {
            (viewHolder as Holder).post_Comment_Name.setText(arrayList.get(i).name)
            (viewHolder as Holder).post_Comment_Script.setText(arrayList.get(i).script)
            (viewHolder as Holder).post_Comment_Date.setText(arrayList.get(i).date)

            //이미지는 보류
            //(viewHolder as Holder).post_Comment_Profile_Image.setImage(arrayList.get(i).profileImage)

        }
        else if(viewHolder is Holder2) {
        }
        else if(viewHolder is Holder3) {
        }
        else if(viewHolder is Holder4) {
        }
        else if(viewHolder is Holder5) {
        }

    }
    //댓글
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val post_Comment_Name :TextView= itemView.findViewById(R.id.post_Comment_Name)
        val post_Comment_Script :TextView= itemView.findViewById(R.id.post_Comment_Script)
        val post_Comment_Date :TextView= itemView.findViewById(R.id.post_Comment_Date)
        val post_Comment_Profile_Image:ImageView = itemView.findViewById(R.id.post_Comment_Profile_Image)
    }
    //비디오 포스트
    inner class Holder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val post_Tv:TextView = itemView.findViewById(R.id.post_Tv)
        val post_Video:VideoView = itemView.findViewById(R.id.post_Video)
    }
    //녹음 포스트
    inner class Holder3(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val post_Tv:TextView = itemView.findViewById(R.id.post_Tv)
        val post_Voice:VideoView = itemView.findViewById(R.id.post_Voice)
    }
    //텍스트 포스트
    inner class Holder4(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val post_Tv:TextView = itemView.findViewById(R.id.post_Tv)
    }
    //이미지 포스트
    inner class Holder5(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val post_Tv:TextView = itemView.findViewById(R.id.post_Tv)
        val post_Photo:VideoView = itemView.findViewById(R.id.post_Photo)
    }

    override fun getItemViewType(position: Int): Int {

        return if (arrayList.get(position).type == 1) {
            1
        } else if (arrayList.get(position).type == 2) {
            2
        } else if(arrayList.get(position).type == 3){
            3
        }else if(arrayList.get(position).type == 4){
            4
        }else if(arrayList.get(position).type == 5){
            5
        }
        else {
            6
        }
    }
}