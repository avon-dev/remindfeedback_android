package com.avon.remindfeedback.CategorySetting

import android.content.Context
import kotlin.collections.ArrayList

interface ContractCategorySetting {
    interface View {
        fun refresh()
        fun showModifyActivity(id: Int, color: String, title: String)
    }

    interface Presenter {

        var view: View
        var context: Context

        fun loadItems(adapterCategorySetting: AdapterCategorySetting, list: ArrayList<ModelCategorySetting>)

        fun addItems(color: String, title: String, mAdapter: AdapterCategorySetting, list: ArrayList<ModelCategorySetting>)

        fun removeItems(id: Int, context: Context)

        fun updateItems(list: ArrayList<ModelCategorySetting>, id: Int, color: String, title: String, adapterCategorySetting: AdapterCategorySetting)

        fun showModifyActivity(id: Int, color: String, title: String)
    }
}