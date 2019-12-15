package com.example.remindfeedback.CategorySetting

import android.content.Context
import java.util.*

interface ContractCategorySetting {
    interface View{
        fun refresh()
    }

    interface Presenter {

        var view: View
        var context: Context

        fun loadItems(adapterCategorySetting: AdapterCategorySetting, list: ArrayList<ModelCategorySetting>)

        fun addItems(color:String, title:String, mAdapter:AdapterCategorySetting)

        fun removeItems(id: Int, context: Context)

//        fun updateItems(id: Int, color: String, title: String)
    }
}