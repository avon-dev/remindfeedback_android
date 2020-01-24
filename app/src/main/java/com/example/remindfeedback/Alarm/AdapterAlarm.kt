package com.example.remindfeedback.Alarm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.example.remindfeedback.R
import java.util.ArrayList

class AdapterAlarm(val context: Context, val arrayList: ArrayList<ModelAlarm>) :
    RecyclerView.Adapter<AdapterAlarm.Holder>() {


    fun addItem(item: ModelAlarm) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(item)

        }
    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false)
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
        val alarm_Date = itemView.findViewById<TextView>(R.id.alarm_Date)
        val alarm_Script = itemView.findViewById<TextView>(R.id.alarm_Script)
        val alarm_Profile_Image = itemView.findViewById<ImageView>(R.id.alarm_Profile_Image)
        val alarm_Alarm = itemView.findViewById<ImageView>(R.id.alarm_Alarm)


        fun bind(alarm_list: ModelAlarm, context: Context) {

            //상대이름, 피드백제목, 피드백 작성일 등 정의해줌
            alarm_Script.text = alarm_list.script
            alarm_Date.text = alarm_list.date

            if (alarm_list.newAlarm) {
            } else {
                alarm_Alarm.visibility = View.INVISIBLE
            }

            //원래 각자의 프로필 이미지를 받아와야하지만 일단 기본이미지로 설정함
            alarm_Profile_Image.setImageResource(R.drawable.ic_default_profile)

            //일단 클릭하면 알람의 타입이 무엇인지 토스트로 띄움
            itemView.setOnClickListener {
                Toast.makeText(context, "" + alarm_list.type, Toast.LENGTH_SHORT).show()
            }

        }
    }

}