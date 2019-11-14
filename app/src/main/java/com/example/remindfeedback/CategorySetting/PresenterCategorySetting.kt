package com.example.remindfeedback.CategorySetting

import android.content.Context
import java.util.ArrayList

class PresenterCategorySetting: ContextCategorySetting.Presenter {
    override lateinit var view: ContextCategorySetting.View


    override fun loadItems(list: ArrayList<ModelCategorySetting>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addItems(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateItems(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}