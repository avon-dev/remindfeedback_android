package com.example.remindfeedback.FeedbackList

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.FeedbackList.FeedbackDetail.FeedbackDetailActivity
import com.example.remindfeedback.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AdapterMainFeedback(
    val context: Context,
    val arrayList: ArrayList<ModelFeedback>,
    var presenterMain: PresenterMain
) : RecyclerView.Adapter<AdapterMainFeedback.Holder>() {
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
        val main_Feedback_Profile_Image =
            itemView.findViewById<ImageView>(R.id.main_Feedback_Profile_Image)
        val main_Feedback_Alarm = itemView.findViewById<ImageView>(R.id.main_Feedback_Alarm)
        val main_Feedback_Name = itemView.findViewById<TextView>(R.id.main_Feedback_Name)
        val main_Feedback_Dday = itemView.findViewById<TextView>(R.id.main_Feedback_Dday)


        fun bind(feedback_list: ModelFeedback, context: Context) {

            //상대이름, 피드백제목, 피드백 작성일 등 정의해줌
            main_Feedback_Name.text = feedback_list.adviser
            main_Feedback_Script.text = feedback_list.script
            main_Feedback_Date.text = feedback_list.date
            main_Feedback_Tag_Color.setBackgroundColor(Color.parseColor(feedback_list.tagColor))


            //피더의 프로필 이미지 정의해주는부분, 원래 각자의 프로필 이미지를 받아와야하지만 일단 기본이미지로 설정함
            main_Feedback_Profile_Image.setImageResource(R.drawable.ic_default_profile)

            //새로운 알람이 와있으면 visible 아니면 invisible
            if (feedback_list.alarm == false) {

            } else {
                main_Feedback_Alarm.visibility = View.INVISIBLE
            }

            //그냥 클릭했을때
            itemView.setOnClickListener {
                val intent = Intent(context, FeedbackDetailActivity::class.java)
                context.startActivity(intent)
            }

            //꾹 눌렀을때
            itemView.setOnLongClickListener {
                var dialogInterface: DialogInterface? = null
                val dialog = AlertDialog.Builder(context)
                val edialog: LayoutInflater = LayoutInflater.from(context)
                val mView: View = edialog.inflate(R.layout.dialog_update_delete, null)

                val update_Tv: TextView = mView.findViewById(R.id.update_Tv)
                val delete_Tv: TextView = mView.findViewById(R.id.delete_Tv)

                update_Tv.setOnClickListener {
                    Log.e("asda", "수정" + adapterPosition)
                    presenterMain.modifyFeedbackActivity(
                        feedback_list.id,
                        feedback_list.date,
                        feedback_list.script
                    )
                    dialogInterface!!.dismiss()
                }
                delete_Tv.setOnClickListener {
                    removeAt(adapterPosition)
                    presenterMain.removeItems(feedback_list.id, context)
                    dialogInterface!!.dismiss()
                }
                dialog.setView(mView)
                dialog.create()
                dialogInterface = dialog.show()


                return@setOnLongClickListener true
            }

            // 디데이 표시
            var nowDate = LocalDate.now()
            var strNow = nowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            // 피드백 날짜 - 형식으로 바꾸기
            var fbSplit = feedback_list.date.split("년 ", "월 ", "일")
            var strDate: String
            for (i in 0..2 step 1) {
                strDate = fbSplit[0] + "-" + fbSplit[1] + "-" + fbSplit[2]

                // 데이트포맷
                var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                // 오늘 타임스탬프
                var todayTimestamp = sdf.parse(strNow).time
                // 비교할 타임스탬프
                var feedbackTimestamp = sdf.parse(strDate).time

                var differ = (feedbackTimestamp - todayTimestamp) / (24 * 60 * 60 * 1000)
                Log.e("디데이 롱롱", differ.toString())
                var differInt = differ.toInt()

                if (differInt == 0) {
                    main_Feedback_Dday.setText("D - Day")
                } else if (differInt < 0){
                    main_Feedback_Dday.setText("D + " + Math.abs(differInt))
                } else if (differInt > 0){
                    main_Feedback_Dday.setText("D - " + differInt)
                }
            }


        }
    }


}