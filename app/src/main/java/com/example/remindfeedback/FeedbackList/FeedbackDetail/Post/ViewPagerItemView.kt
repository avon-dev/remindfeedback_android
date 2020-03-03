package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.remindfeedback.R
import com.example.remindfeedback.etcProcess.URLtoBitmapTask
import com.squareup.picasso.Picasso

import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class ViewPagerItemView(context: Context) : FrameLayout(context) {

    private val mImageView: ImageView
    private var mBitmap: Bitmap? = null //bitmap of the image
    private var mJSONObject: JSONObject? = null

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.viewpager_itemview, null)
        mImageView = view.findViewById(R.id.imageView) as ImageView
        addView(view)
    }

    fun recycle() {
        mImageView.setImageBitmap(null)

        if (mBitmap == null || mBitmap!!.isRecycled) {
            return
        }

        mBitmap!!.recycle()
        mBitmap = null
    }

    fun setData(jsonObject: JSONObject) {
        mJSONObject = jsonObject
        try {
            //val resId = jsonObject.getInt("id")
            val resId = jsonObject.getString("id")
            val name = jsonObject.getString("name")

            //이미지 설정해주는 부분
            Picasso.get().load(resId).into(mImageView)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun reload() { //bitmap maybe recycled
        try {
            val resId = mJSONObject!!.getString("id")
            //이미지 설정해주는 부분
            Picasso.get().load(resId).into(mImageView)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

}
