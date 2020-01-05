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

import org.json.JSONException
import org.json.JSONObject
import java.net.URL

/**
 * Created by wilson on 2015/9/6.
 */
class ViewPagerItemView(context: Context) : FrameLayout(context) {

    private val mImageView: ImageView
    private val mNameTextView: TextView

    private var mBitmap: Bitmap? = null //bitmap of the image
    private var mJSONObject: JSONObject? = null

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.viewpager_itemview, null)
        mImageView = view.findViewById(R.id.imageView) as ImageView
        mNameTextView = view.findViewById(R.id.textView_name) as TextView
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
            var image_task: URLtoBitmapTask = URLtoBitmapTask()
            image_task = URLtoBitmapTask().apply {
                url = URL(resId)
            }
            mBitmap = image_task.execute().get()
            mImageView.setImageBitmap(mBitmap)
            mNameTextView.text = name
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun reload() { //bitmap maybe recycled
        try {
            val resId = mJSONObject!!.getInt("id")
            mBitmap = BitmapFactory.decodeResource(resources, resId)
            mImageView.setImageBitmap(mBitmap)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

}
