package com.example.remindfeedback.CategorySetting.CreateCategory.ColorList

import android.content.Context

class PresenterColorList : ContractColorList.Presenter {

    override lateinit var view: ContractColorList.View
    override lateinit var context: Context

    override fun returnColor(color: String) {
        view.returnColor(color)
    }

}