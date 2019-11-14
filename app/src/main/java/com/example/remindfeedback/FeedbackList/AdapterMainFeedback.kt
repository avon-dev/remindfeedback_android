package com.example.remindfeedback.FeedbackList

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.FeedbackList.FeedbackDetail.FeedbackDetailActivity
import com.example.remindfeedback.R
import java.net.URL
import java.util.ArrayList

class AdapterMainFeedback(val context: Context, val arrayList: ArrayList<ModelFeedback>) :   RecyclerView.Adapter<AdapterMainFeedback.Holder>() {


    fun addItem(item: ModelFeedback) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(item)

        }
    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main_feedback, parent, false)
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
        val main_Feedback_Tag_Color = itemView.findViewById<TextView>(R.id.main_Feedback_Tag_Color)
        val main_Feedback_Script = itemView.findViewById<TextView>(R.id.main_Feedback_Script)
        val main_Feedback_Date = itemView.findViewById<TextView>(R.id.main_Feedback_Date)
        val main_Feedback_Profile_Image = itemView.findViewById<ImageView>(R.id.main_Feedback_Profile_Image)
        val main_Feedback_Alarm = itemView.findViewById<ImageView>(R.id.main_Feedback_Alarm)
        val main_Feedback_Name = itemView.findViewById<TextView>(R.id.main_Feedback_Name)



        fun bind (feedback_list: ModelFeedback, context: Context) {

            //상대이름, 피드백제목, 피드백 작성일 등 정의해줌
            main_Feedback_Name.text = feedback_list.feederName
            main_Feedback_Script.text = feedback_list.script
            main_Feedback_Date.text = feedback_list.date

            //태그의 색을 정의해주는부분
            //만약 tagColor의 값이 blue라면 배경 색깔을 blue로 표시해줌
            if(feedback_list.tagColor.equals("blue")){
                main_Feedback_Tag_Color.setBackgroundColor(Color.BLUE)
            }else{
                //색깔마다 조건문을 만들어줘야하지만 아직 정해지지않은 임시이기 때문에 나머지는 red로 표기할것
                main_Feedback_Tag_Color.setBackgroundColor(Color.RED)
            }

            //피더의 프로필 이미지 정의해주는부분, 원래 각자의 프로필 이미지를 받아와야하지만 일단 기본이미지로 설정함
            main_Feedback_Profile_Image.setImageResource(R.drawable.ic_default_profile)

            //새로운 알람이 와있으면 visible 아니면 invisible
            if(feedback_list.alarm == false){

            }else{
                main_Feedback_Alarm.visibility = View.INVISIBLE
            }

            itemView.setOnClickListener {
                val intent = Intent(context, FeedbackDetailActivity::class.java)
                context.startActivity(intent)
            }

        }
    }



}