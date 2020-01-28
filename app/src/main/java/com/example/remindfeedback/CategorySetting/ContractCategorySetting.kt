package com.example.remindfeedback.CategorySetting

import android.content.Context
import java.util.*

interface ContractCategorySetting {
    interface View {
        fun refresh()
        fun showModifyActivity(id: Int, color: String, title: String)
    }

    interface Presenter {

        var view: View
        var context: Context

        fun loadItems(
            adapterCategorySetting: AdapterCategorySetting,
            list: ArrayList<ModelCategorySetting>
        )

        fun addItems(color: String, title: String, mAdapter: AdapterCategorySetting)

        fun removeItems(id: Int, context: Context)

        fun updateItems(
            list: ArrayList<ModelCategorySetting>,
            id: Int,
            color: String,
            title: String,
            adapterCategorySetting: AdapterCategorySetting
        )

        fun showModifyActivity(id: Int, color: String, title: String)
    }
}