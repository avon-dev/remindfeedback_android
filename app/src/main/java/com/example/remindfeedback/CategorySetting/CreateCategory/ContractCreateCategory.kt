package com.example.remindfeedback.CategorySetting.CreateCategory

import android.content.Context

interface ContractCreateCategory {
    interface View {
    }

    interface Presenter {
        var view: View
        var context: Context
    }
}