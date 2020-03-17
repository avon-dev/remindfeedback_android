package com.avon.remindfeedback.Alarm

import android.content.Context
import java.util.ArrayList

class PresenterAlarm : ContractAlarm.Presenter {


    lateinit override var view: ContractAlarm.View
    //알람만 불러오는거
    override fun loadAlarm(list: ArrayList<ModelAlarm>) {
    }

    //피드백만 불러오는거
    override fun loadFeedback(list: ArrayList<ModelAlarm>) {
    }

    //다불러오는거
    override fun loadItems(list: ArrayList<ModelAlarm>) {
    }

    override fun addItems(position: Int) {
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }
}