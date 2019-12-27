package com.example.remindfeedback.CategorySetting.CreateCategory.ColorList

import android.content.Context
import com.example.remindfeedback.CategorySetting.AdapterCategorySetting
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import java.util.ArrayList

interface ContractPickCategory {
    interface View{
        fun refresh()
        fun returnData(modelPickCategory: ModelPickCategory)
    }

    interface Presenter {

        var view: View
        var context: Context
        fun returnData(modelPickCategory: ModelPickCategory)
        fun getData(arrayData:ArrayList<ModelPickCategory>)
    }
}