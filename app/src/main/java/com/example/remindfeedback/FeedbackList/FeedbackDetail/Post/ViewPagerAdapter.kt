package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.HashMap

/**
 * Created by wilson on 2015/9/6.
 * An example of ViewPagerAdapter
 */
class ViewPagerAdapter(private val mContext: Context, private val mJSONArray: JSONArray //data source
) : PagerAdapter() {

    private val mHashMap: HashMap<Int, ViewPagerItemView>

    init {
        mHashMap = HashMap()
    }

    override fun getCount(): Int {
        return mJSONArray.length()
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    //call when need to instantiate and Item, much like getView() in ListView adapter
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("gyw", "ViewPagerAdapter::instantiateItem(); position:$position")
        val itemView: ViewPagerItemView
        if (mHashMap.containsKey(position)) { //view is cached but bitmap maybe recycled
            itemView = mHashMap[position]!!
            itemView.reload()
        } else {
            itemView = ViewPagerItemView(mContext)
            try {
                val jsonObject = mJSONArray.getJSONObject(position)
                itemView.setData(jsonObject)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            mHashMap[position] = itemView
            container.addView(itemView)
        }
        return itemView
    }

    //call when flip away; recycle behavior is defined here
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        Log.d("gyw", "ViewPagerAdapter::destroyItem(); position:$position")
        (`object` as ViewPagerItemView).recycle()
    }

}
