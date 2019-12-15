package com.example.remindfeedback.CategorySetting

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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.CategorySetting.ModifyCategory.ModifyCategoryActivity
import com.example.remindfeedback.R
import java.util.*


class AdapterCategorySetting(val context: Context, val arrayList: ArrayList<ModelCategorySetting>, var presenterCategorySetting: PresenterCategorySetting) :   RecyclerView.Adapter<AdapterCategorySetting.Holder>()  {


    fun addItem(item: ModelCategorySetting) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(item)

        }
    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category_setting, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position], context)

        // 아이템 클릭 (추후 삭제)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category_Color = itemView.findViewById<TextView>(R.id.category_Color)
        val category_Title = itemView.findViewById<TextView>(R.id.category_Title)
        val category_More = itemView.findViewById<ImageView>(R.id.category_More)


        fun bind (categorySetting: ModelCategorySetting, context: Context) {
            //헥사코드인식해서 배경으로 만듦
            category_Color.setBackgroundColor(Color.parseColor(categorySetting.color))
            Log.e("category_Color", categorySetting.color)
            category_Title.text = categorySetting.title


            category_More.setOnClickListener {
                var dialogInterface: DialogInterface? = null
                val dialog = AlertDialog.Builder(context)
                val edialog : LayoutInflater = LayoutInflater.from(context)
                val mView : View = edialog.inflate(R.layout.dialog_update_delete,null)

                val update_Tv : TextView = mView.findViewById(R.id.update_Tv)
                val delete_Tv : TextView = mView.findViewById(R.id.delete_Tv)

                update_Tv.setOnClickListener{
                    Log.e("주제 수정", "수정누름" + adapterPosition)

                    val intent = Intent(context, ModifyCategoryActivity::class.java)
                    intent.putExtra("id", categorySetting.id)
                    intent.putExtra("title", categorySetting.title)
                    intent.putExtra("color", categorySetting.color)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)

//                    presenterCategorySetting.updateItems(categorySetting.id, categorySetting.color, categorySetting.title)
                    dialogInterface!!.dismiss()
                }
                delete_Tv.setOnClickListener{
                    removeAt(adapterPosition)
                    presenterCategorySetting.removeItems(categorySetting.id, context)
                    dialogInterface!!.dismiss()
                }
                dialog.setView(mView)
                dialog.create()
                dialogInterface = dialog.show()
            }

        }
    }


}