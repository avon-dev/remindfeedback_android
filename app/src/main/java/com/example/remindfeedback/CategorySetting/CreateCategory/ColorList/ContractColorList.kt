package com.example.remindfeedback.CategorySetting.CreateCategory.ColorList

import android.content.Context
import com.example.remindfeedback.CategorySetting.AdapterCategorySetting
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import java.util.ArrayList

interface ContractColorList {
    interface View{
        fun refresh()
        fun returnColor(color:String)
    }

    interface Presenter {

        var view: View
        var context: Context
        fun returnColor(color:String)

    }
}