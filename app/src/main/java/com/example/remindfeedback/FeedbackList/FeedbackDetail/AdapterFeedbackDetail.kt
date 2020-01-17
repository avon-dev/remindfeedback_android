package com.example.remindfeedback.FeedbackList.FeedbackDetail

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.FeedbackList.FeedbackDetail.Post.PostActivity
import com.example.remindfeedback.R
import java.util.ArrayList

class AdapterFeedbackDetail(val context: Context, val arrayList: ArrayList<ModelFeedbackDetail>, val presenterFeedbackDetail: PresenterFeedbackDetail) :   RecyclerView.Adapter<AdapterFeedbackDetail.Holder>()  {


    fun addItem(item: ModelFeedbackDetail) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(item)

        }
    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_feedback_detail, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position], context)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val feedback_Detail_Title = itemView.findViewById<TextView>(R.id.feedback_Detail_Title_Tv)
        val feedback_Detail_Date = itemView.findViewById<TextView>(R.id.feedback_Detail_Date_Tv)
        val feedback_Detail_Contents = itemView.findViewById<TextView>(R.id.feedback_Detail_Contents)
        val feedback_Detail_Image = itemView.findViewById<ImageView>(R.id.feedback_Detail_Image)
        val feedback_Detail_More = itemView.findViewById<ImageView>(R.id.feedback_Detail_More)


        fun bind (feedback_detail_list: ModelFeedbackDetail, context: Context) {
            feedback_Detail_Title.text = feedback_detail_list.title
            feedback_Detail_Date.text = feedback_detail_list.date
            //0(글), 1(사진), 2(영상), 3(녹음)
            if(feedback_detail_list.contents_type == 2){
                feedback_Detail_Contents.text = "비디오"
                feedback_Detail_Image.setImageResource(R.drawable.ic_video_black)
            }else if(feedback_detail_list.contents_type == 0){
                feedback_Detail_Contents.text = "글"
                feedback_Detail_Image.setImageResource(R.drawable.ic_text)
            }else if(feedback_detail_list.contents_type== 3){
                feedback_Detail_Contents.text = "음성"
                feedback_Detail_Image.setImageResource(R.drawable.ic_voice_black)
            }else if(feedback_detail_list.contents_type == 1){
                feedback_Detail_Contents.text = "사진"
                feedback_Detail_Image.setImageResource(R.drawable.ic_photo_black)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, PostActivity::class.java)
                intent.putExtra("feedback_id", feedback_detail_list.feedback_id)
                intent.putExtra("board_id", feedback_detail_list.board_id)
                context.startActivity(intent)
            }

            feedback_Detail_More.setOnClickListener {
                var dialogInterface: DialogInterface? = null
                val dialog = AlertDialog.Builder(context)
                val edialog : LayoutInflater = LayoutInflater.from(context)
                val mView : View = edialog.inflate(R.layout.dialog_update_delete,null)

                val update_Tv : TextView = mView.findViewById(R.id.update_Tv)
                val delete_Tv : TextView = mView.findViewById(R.id.delete_Tv)

                update_Tv.setOnClickListener{
                    Log.e("textTypeBoard 수정", "수정한다"+adapterPosition)
                    presenterFeedbackDetail.modifyBoardActivity(feedback_detail_list.feedback_id, feedback_detail_list.board_id, feedback_detail_list.title, feedback_detail_list.content)
                    dialogInterface!!.dismiss()
                }
                delete_Tv.setOnClickListener{
                    removeAt(adapterPosition)
                    presenterFeedbackDetail.removeItems(feedback_detail_list.board_id, context)
                    dialogInterface!!.dismiss()
                }
                dialog.setView(mView)
                dialog.create()
                dialogInterface = dialog.show()
            }

        }
    }


}