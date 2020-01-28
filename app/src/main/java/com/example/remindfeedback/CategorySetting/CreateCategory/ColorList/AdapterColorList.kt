package com.example.remindfeedback.CategorySetting.CreateCategory.ColorList

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.R
import java.util.ArrayList


class AdapterColorList(
    val context: Context,
    val arrayList: ArrayList<ModelColorList>,
    var presenterColorList: PresenterColorList
) : RecyclerView.Adapter<AdapterColorList.Holder>() {

    fun addItem(item: ModelColorList) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(item)
        }
    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_color_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val color_Base_Tv = itemView.findViewById<TextView>(R.id.color_Base_Tv)
        val color_Holder = itemView.findViewById<TextView>(R.id.color_Holder)


        fun bind(colorList: ModelColorList, context: Context) {
            //헥사코드인식해서 배경으로 만듦
            color_Base_Tv.setBackgroundColor(Color.parseColor(colorList.color))
            color_Holder.text = colorList.color
            Log.e("tag", colorList.color)
            color_Base_Tv.setOnClickListener() {
                color_Base_Tv.text = "✔"
                val intent = Intent()
                presenterColorList.returnColor(color_Holder.text.toString())


            }
        }
    }
}