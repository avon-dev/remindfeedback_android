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





class AdapterPickCategory(val context: Context, val arrayList: ArrayList<ModelPickCategory>, var presenterPickCategory: PresenterPickCategory) :   RecyclerView.Adapter<AdapterPickCategory.Holder>() {

    fun addItem(item: ModelPickCategory) {//아이템 추가
        if (arrayList != null) {//널체크 해줘야함
            arrayList.add(item)
        }
    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pick_category, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pick_Category_Color_Tv = itemView.findViewById<TextView>(R.id.pick_Category_Color_Tv)
        val pick_Category_Title_Tv = itemView.findViewById<TextView>(R.id.pick_Category_Title_Tv)
        val pick_Category_Id_Tv = itemView.findViewById<TextView>(R.id.pick_Category_Id_Tv)


        fun bind (colorList: ModelPickCategory, context: Context) {
            //헥사코드인식해서 배경으로 만듦
            pick_Category_Color_Tv.setBackgroundColor(Color.parseColor(colorList.color))
            pick_Category_Title_Tv.text = colorList.title
            pick_Category_Id_Tv.text = colorList.id.toString()
            itemView.setOnClickListener(){
                presenterPickCategory.returnData(ModelPickCategory(colorList.id, colorList.color, colorList.title))
            }
        }
    }
}