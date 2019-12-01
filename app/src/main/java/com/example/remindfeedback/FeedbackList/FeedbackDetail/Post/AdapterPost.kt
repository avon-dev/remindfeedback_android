package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.FeedbackList.FeedbackDetail.AdapterFeedbackDetail
import com.example.remindfeedback.FeedbackList.FeedbackDetail.ModelFeedbackDetail
import com.example.remindfeedback.R
import java.util.ArrayList

class AdapterPost(val context: Context, val arrayList: ArrayList<ModelPost>) :   RecyclerView.Adapter<AdapterPost.Holder>() {

    fun addItem(item: ModelPost) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(item)

        }
    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post_comment, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position], context)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val post_Comment_Name = itemView.findViewById<TextView>(R.id.post_Comment_Name)
        val post_Comment_Script = itemView.findViewById<TextView>(R.id.post_Comment_Script)
        val post_Comment_Date = itemView.findViewById<TextView>(R.id.post_Comment_Date)
        val post_Comment_Profile_Image = itemView.findViewById<ImageView>(R.id.post_Comment_Profile_Image)
        val post_Comment_More = itemView.findViewById<ImageView>(R.id.post_Comment_More)


        fun bind (comment_list: ModelPost, context: Context) {

            post_Comment_Name.text = comment_list.name
            post_Comment_Script.text = comment_list.script
            post_Comment_Date.text = comment_list.date


            itemView.setOnClickListener {

            }

        }
    }
}