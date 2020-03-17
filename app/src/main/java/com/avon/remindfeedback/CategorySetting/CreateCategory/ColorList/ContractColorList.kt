package com.avon.remindfeedback.CategorySetting.CreateCategory.ColorList

import android.content.Context

interface ContractColorList {
    interface View {
        fun refresh()
        fun returnColor(color: String)
    }

    interface Presenter {

        var view: View
        var context: Context
        fun returnColor(color: String)

    }
}