package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.FeedbackList.FeedbackDetail.AdapterFeedbackDetail
import com.example.remindfeedback.FeedbackList.FeedbackDetail.ModelFeedbackDetail
import com.example.remindfeedback.R
import com.example.remindfeedback.etcProcess.URLtoBitmapTask
import kotlinx.android.synthetic.main.activity_my_page.*
import java.net.URL
import java.util.ArrayList

class AdapterPost(val context: Context, val arrayList: ArrayList<ModelComment>) :   RecyclerView.Adapter<AdapterPost.Holder>() {

    fun addItem(item: ModelComment) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(0,item)

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


        fun bind (comment_list: ModelComment, context: Context) {

            post_Comment_Name.text = comment_list.name
            post_Comment_Script.text = comment_list.script
            post_Comment_Date.text = comment_list.date

            if(!comment_list.profileImage.equals("")){
                //이미지 설정해주는 부분
                var test_task: URLtoBitmapTask = URLtoBitmapTask()
                test_task = URLtoBitmapTask().apply {
                    url = URL("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/"+comment_list.profileImage)
                }
                var bitmap: Bitmap = test_task.execute().get()
                post_Comment_Profile_Image.setImageBitmap(bitmap)
            }


            itemView.setOnClickListener {

            }

        }
    }
}