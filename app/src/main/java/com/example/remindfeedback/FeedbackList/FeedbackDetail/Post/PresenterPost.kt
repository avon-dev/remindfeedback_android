package com.example.remindfeedback.FeedbackList.FeedbackDetail.Post

import android.content.Context
import android.graphics.ColorSpace
import android.view.Display
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PresenterPost:ContractPost.Presenter {
    lateinit override var view: ContractPost.View


    override fun loadItems(list: ArrayList<ModelPost>) {
    }

    override fun addItems(adapterPost: AdapterPost,comment:String) {
        val tz = TimeZone.getTimeZone("Asia/Seoul")
        val gc = GregorianCalendar(tz)
        var year = gc.get(GregorianCalendar.YEAR).toString()
        var month = gc.get(GregorianCalendar.MONTH).toString()
        var day = gc.get(GregorianCalendar.DATE).toString()
        var hour= gc.get(GregorianCalendar.HOUR).toString()
        var min = gc.get(GregorianCalendar.MINUTE).toString()
        //var sec = gc.get(GregorianCalendar.SECOND).toString()


        var modelPost:ModelPost = ModelPost("dummy", "내이름", comment, "$year 년 $month 월 $day 일 $hour 시 $min 분",1)
        adapterPost.addItem(modelPost)
        view.refresh()
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }
}