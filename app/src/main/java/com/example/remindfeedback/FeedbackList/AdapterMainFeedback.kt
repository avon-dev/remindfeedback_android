package com.example.remindfeedback.FeedbackList

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.FeedbackList.FeedbackDetail.FeedbackDetailActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.etcProcess.URLtoBitmapTask
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AdapterMainFeedback(
    val context: Context,
    val arrayList: ArrayList<ModelFeedback?>,
    var presenterMain: PresenterMain,
    val feedbackMyYour: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun addItem(item: ModelFeedback) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(item)
        }
    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_new_main, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holder) {
            holder.bind(arrayList[position], context)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }

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


        fun bind(feedback_list: ModelFeedback?, context: Context) {
            //상대이름, 피드백제목, 피드백 작성일 등 정의해줌
            if (feedback_list != null) {
                main_Feedback_Name.text = feedback_list.adviser
            }
            if (feedback_list != null) {
                main_Feedback_Script.text = feedback_list.title
            }
            if (feedback_list != null) {
                main_Feedback_Date.text = feedback_list.date
            }
            if (feedback_list != null) {
                main_Feedback_Tag_Color.setBackgroundColor(Color.parseColor(feedback_list.tagColor))
            }
            if (!feedback_list!!.feederProfileImage.equals("")) {
                main_Feedback_Profile_Image.visibility = View.VISIBLE//조언자가 있을때
                main_Feedback_Alarm.visibility = View.INVISIBLE//일단 안보이게
                var test_task: URLtoBitmapTask = URLtoBitmapTask()
                test_task = URLtoBitmapTask().apply {
                    url =
                        URL("https://remindfeedback.s3.ap-northeast-2.amazonaws.com/" + feedback_list.feederProfileImage)
                }
                var bitmap: Bitmap = test_task.execute().get()
                main_Feedback_Profile_Image.setImageBitmap(bitmap)
            } else {
                if(feedback_list.adviser.equals("")){
                    main_Feedback_Profile_Image.visibility = View.VISIBLE//조언자가 없을때
                    main_Feedback_Alarm.visibility = View.INVISIBLE
                    main_Feedback_Profile_Image.setImageResource(R.drawable.ourlogo)

                }else{
                    main_Feedback_Profile_Image.visibility = View.VISIBLE//조언자가 있을때
                    main_Feedback_Alarm.visibility = View.INVISIBLE//일단 안보이게
                    main_Feedback_Profile_Image.setImageResource(R.drawable.ic_default_profile)
                }

            }

            //임시로 색깔 넣어줌 시각적으로 보기위해
            when (feedback_list.complete) {
                -1 -> {
                    itemView.setBackgroundColor(Color.WHITE)
                }//어떤 상태도 아님
                0 -> {
                    itemView.setBackgroundColor(Color.RED)
                }//피드백 완료 거절 상태
                1 -> {
                    itemView.setBackgroundColor(Color.parseColor("#E1EFEA"))
                }//피드백 완료 요청 상태
                2 -> {
                    itemView.setBackgroundColor(Color.parseColor("#ACFF96"))
                }//피드백 완료 수락 상태
            }


            //새로운 알람이 와있으면 visible 아니면 invisible
            if (feedback_list != null) {
                if (feedback_list.alarm == false) {

                } else {
                    main_Feedback_Alarm.visibility = View.INVISIBLE
                }
            }

            //그냥 클릭했을때
            itemView.setOnClickListener {
                val intent = Intent(context, FeedbackDetailActivity::class.java)
                intent.putExtra("feedback_id", feedback_list.feedback_Id)
                intent.putExtra("title", feedback_list.title)
                intent.putExtra("date", feedback_list.date)
                intent.putExtra("feedbackMyYour", feedbackMyYour)
                intent.putExtra("complete", feedback_list.complete)
                intent.putExtra("adviser", feedback_list.adviser)
                context.startActivity(intent)
            }

            //꾹 눌렀을때
            itemView.setOnLongClickListener {
                if(feedbackMyYour == 0 || feedbackMyYour == 2){
                    var dialogInterface: DialogInterface? = null
                    val dialog = AlertDialog.Builder(context)
                    val edialog: LayoutInflater = LayoutInflater.from(context)
                    val mView: View = edialog.inflate(R.layout.dialog_update_delete, null)

                    val update_Tv: TextView = mView.findViewById(R.id.update_Tv)
                    val delete_Tv: TextView = mView.findViewById(R.id.delete_Tv)

                    update_Tv.setOnClickListener {
                        if(feedbackMyYour == 0){
                            Log.e("asda", "수정" + adapterPosition)
                            if (feedback_list != null) {
                                presenterMain.modifyFeedbackActivity(
                                    feedback_list.feedback_Id,
                                    feedback_list.category,
                                    feedback_list.date,
                                    feedback_list.title
                                )
                            }

                        }else{
                            Toast.makeText(context, "이미 완료된 피드백은 수정할 수 없습니다.",Toast.LENGTH_LONG).show()
                        }
                        dialogInterface!!.dismiss()
                    }
                    delete_Tv.setOnClickListener {
                        if (feedback_list != null) {
                            removeAt(adapterPosition)
                            presenterMain.removeItems(feedback_list.feedback_Id, context)
                        }
                        dialogInterface!!.dismiss()
                    }
                    dialog.setView(mView)
                    dialog.create()
                    dialogInterface = dialog.show()
                }

                return@setOnLongClickListener true
            }

            if(feedback_list.complete != 2){//완료가 아닐경우
                main_Feedback_Dday.visibility = View.VISIBLE
                // 디데이
                var nowDate = LocalDate.now()
                var strNow = nowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                // 피드백 날짜 - 형식으로 바꾸기
                var fbSplit = feedback_list?.date!!.split("년 ", "월 ", "일")
                var fbDate: String
                for (i in 0..2 step 1) {
                    fbDate = fbSplit[0] + "-" + fbSplit[1] + "-" + fbSplit[2]
                    // 데이트포맷
                    var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                    // 오늘 타임스탬프
                    var todayTimestamp = sdf.parse(strNow).time
                    // 비교할 타임스탬프
                    var feedbackTimestamp = sdf.parse(fbDate).time

                    var differ = (feedbackTimestamp - todayTimestamp) / (24 * 60 * 60 * 1000)
                    var differInt = differ.toInt()

                    if (differInt == 0) {
                        main_Feedback_Dday.setText("D - Day")
                    } else if (differInt < 0) {
                        main_Feedback_Dday.setText("D + " + Math.abs(differInt))
                    } else if (differInt > 0) {
                        main_Feedback_Dday.setText("D - " + differInt)
                    }
                }
            }else{//완료일경우
                main_Feedback_Dday.visibility = View.GONE
            }



        }
    }

    private inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar

        init {
            progressBar = view.findViewById(R.id.progressBar1) as ProgressBar
        }
    }


}