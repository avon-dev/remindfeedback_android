package com.example.remindfeedback.Alarm

import android.content.Context
import java.util.ArrayList

class PresenterAlarm : ContractAlarm.Presenter{

    lateinit override var view: ContractAlarm.View

    override fun loadItems(list: ArrayList<ModelAlarm>) {
    }

    override fun addItems(position: Int) {
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }
}