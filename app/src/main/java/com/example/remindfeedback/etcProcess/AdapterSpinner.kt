package com.example.remindfeedback.etcProcess


import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.remindfeedback.R

/**
 * Created by gwon on 2016-12-03.
 */

class AdapterSpinner(internal var context: Context, internal var data: List<String>?, internal var color_data: List<String>?, internal var id_data: List<Int>?) : BaseAdapter() {
    internal var inflater: LayoutInflater


    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }


    override fun getCount(): Int {

        return if (data != null)
            data!!.size
        else
            0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_create_category, parent, false)
        }

        if (data != null) {
            //데이터세팅
            val text = data!![position]
            if (convertView != null) {
                (convertView.findViewById<View>(R.id.spinnerText) as TextView).text = text
            }
        }
        if(color_data != null){
            val color = color_data!![position]
            if (convertView != null) {
                (convertView.findViewById<View>(R.id.tag_Color) as TextView).setBackgroundColor(Color.parseColor(color))
            }
        }

        Log.e("aaasssdd", data!![position])
        Log.e("aaasssdd", color_data!![position])
        return convertView!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_create_category, parent, false)
        }
        //데이터세팅
        val text = data!![position]
        (convertView!!.findViewById<View>(R.id.spinnerText) as TextView).text = text
        val color = color_data!![position]
        (convertView.findViewById<View>(R.id.tag_Color) as TextView).setBackgroundColor(Color.parseColor(color))

        return convertView!!
    }

    override fun getItem(position: Int): Any {

        return data!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}


