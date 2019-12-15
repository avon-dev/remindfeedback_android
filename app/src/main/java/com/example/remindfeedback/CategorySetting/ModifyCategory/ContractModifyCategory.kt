package com.example.remindfeedback.CategorySetting.ModifyCategory

import android.content.Context

interface ContractModifyCategory {
    interface View {
        fun setData()
    }

    interface Presenter {
        var view: View
        var context: Context

        fun updateItems(id: Int, color: String, title: String)
    }
}