package com.example.remindfeedback.FeedbackList

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infinity_ex2.OnLoadMoreListener
import com.example.remindfeedback.FeedbackList.FeedbackDetail.FeedbackDetailActivity
import com.example.remindfeedback.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AdapterMainFeedback(recyclerView: RecyclerView,val context: Context, val arrayList : ArrayList<ModelFeedback?>, var presenterMain: PresenterMain, private val activity: Activity) :   RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var isLoading: Boolean = false
    private val visibleThreshold = 5
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener!!.onLoadMore()
                    }
                    isLoading = true
                }
            }
        })
        Log.e("로딩", "로딩")
    }
    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener
    }

    override fun getItemViewType(position: Int): Int {
        return if (arrayList!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }


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
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_main_feedback, parent, false)
            return Holder(view)
        } else if (viewType == VIEW_TYPE_LOADING) {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(view)
        }

        val view = LayoutInflater.from(context).inflate(R.layout.item_main_feedback, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    fun setLoaded() {
        isLoading = false
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is Holder){
            holder.bind(arrayList[position], context)
        }else if(holder is LoadingViewHolder){
            holder.progressBar.isIndeterminate = true
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //모델의 변수들 정의하는부분
        val main_Feedback_Tag_Color = itemView.findViewById<TextView>(R.id.main_Feedback_Tag_Color)
        val main_Feedback_Script = itemView.findViewById<TextView>(R.id.main_Feedback_Script)
        val main_Feedback_Date = itemView.findViewById<TextView>(R.id.main_Feedback_Date)
        val main_Feedback_Profile_Image = itemView.findViewById<ImageView>(R.id.main_Feedback_Profile_Image)
        val main_Feedback_Alarm = itemView.findViewById<ImageView>(R.id.main_Feedback_Alarm)
        val main_Feedback_Name = itemView.findViewById<TextView>(R.id.main_Feedback_Name)
        val main_Feedback_Dday = itemView.findViewById<TextView>(R.id.main_Feedback_Dday)



        fun bind (feedback_list: ModelFeedback?, context: Context) {

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


            //피더의 프로필 이미지 정의해주는부분, 원래 각자의 프로필 이미지를 받아와야하지만 일단 기본이미지로 설정함
            main_Feedback_Profile_Image.setImageResource(R.drawable.ic_default_profile)

            //새로운 알람이 와있으면 visible 아니면 invisible
            if (feedback_list != null) {
                if(feedback_list.alarm == false){

                }else{
                    main_Feedback_Alarm.visibility = View.INVISIBLE
                }
            }

            //그냥 클릭했을때
            itemView.setOnClickListener {
                val intent = Intent(context, FeedbackDetailActivity::class.java)
                if (feedback_list != null) {
                    intent.putExtra("feedback_id", feedback_list.feedback_Id)
                }
                if (feedback_list != null) {
                    intent.putExtra("title", feedback_list.title)
                }
                if (feedback_list != null) {
                    intent.putExtra("date", feedback_list.date)
                }
                context.startActivity(intent)
            }

            //꾹 눌렀을때
            itemView.setOnLongClickListener{
                var dialogInterface: DialogInterface? = null
                val dialog = AlertDialog.Builder(context)
                val edialog : LayoutInflater = LayoutInflater.from(context)
                val mView : View = edialog.inflate(R.layout.dialog_update_delete,null)

                val update_Tv : TextView = mView.findViewById(R.id.update_Tv)
                val delete_Tv : TextView = mView.findViewById(R.id.delete_Tv)

                update_Tv.setOnClickListener{
                    Log.e("asda", "수정"+adapterPosition)
                    if (feedback_list != null) {
                        presenterMain.modifyFeedbackActivity(feedback_list.feedback_Id,feedback_list.category, feedback_list.date, feedback_list.title)
                    }
                    dialogInterface!!.dismiss()
                }
                delete_Tv.setOnClickListener{
                    removeAt(adapterPosition)
                    if (feedback_list != null) {
                        presenterMain.removeItems(feedback_list.feedback_Id, context)
                    }
                    dialogInterface!!.dismiss()
                }
                dialog.setView(mView)
                dialog.create()
                dialogInterface = dialog.show()


                return@setOnLongClickListener true
            }

            // 디데이
            var nowDate = LocalDate.now()
            var strNow = nowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            // 피드백 날짜 - 형식으로 바꾸기
            var fbSplit = feedback_list?.date!!.split("년 ", "월 ", "일")
            Log.e("디데이", fbSplit.toString())
            var fbDate: String
            for (i in 0..2 step 1) {
                fbDate = fbSplit[0] + "-" + fbSplit[1] + "-" + fbSplit[2]
                Log.e("디데이", fbDate)
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
                } else if (differInt < 0){
                    main_Feedback_Dday.setText("D + " + Math.abs(differInt))
                } else if (differInt > 0){
                    main_Feedback_Dday.setText("D - " + differInt)
                }
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