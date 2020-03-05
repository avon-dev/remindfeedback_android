package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.FeedbackList.FeedbackDetail.Post.SampleRecyclerAdapter.TextViewHolder
import com.google.gson.JsonObject
import com.squareup.moshi.Json
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class SampleRecyclerAdapter(protected var mCount: Int, var arrayList: ArrayList<String>) : RecyclerView.Adapter<TextViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bindView(arrayList,position)
    }

    override fun getItemCount(): Int {
        return mCount
    }

    fun add() {
        val position = mCount
        mCount++
        notifyItemInserted(position)
    }

    fun remove() {
        if (mCount == 0) {
            return
        }
        mCount--
        val position = mCount
        notifyItemRemoved(position)
    }

    class TextViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val random = Random()
        fun bindView(arrayList: ArrayList<String>, position: Int) {
            val textView = itemView as ImageView
            Picasso.get().load(arrayList[position]).into(textView)

        }

        companion object {
            fun createViewHolder(parent: ViewGroup): TextViewHolder {
                val textView =
                    ImageView(parent.context)
                //textView.setGravity(Gravity.CENTER);
                textView.layoutParams = RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.MATCH_PARENT
                )
                return TextViewHolder(textView)
            }
        }
    }

}