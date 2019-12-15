package com.example.remindfeedback.CategorySetting.CreateCategory

import android.content.Context

interface ContractCreateCategory {
    interface View {
        fun setData()
    }

    interface Presenter {
        var view: View
        var context: Context
    }
}