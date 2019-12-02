package com.example.remindfeedback.CategorySetting

import android.content.Context
import android.content.res.AssetManager
import com.example.remindfeedback.FriendsList.ModelFriendsList
import org.json.JSONObject
import java.util.ArrayList

class PresenterCategorySetting: ContextCategorySetting.Presenter {
    override lateinit var view: ContextCategorySetting.View


    override fun loadItems(list: ArrayList<ModelCategorySetting>) {
    }

    override fun addItems(title:String, mAdapter:AdapterCategorySetting) {
        var modelCategorySetting:ModelCategorySetting = ModelCategorySetting("",title )
        mAdapter.addItem(modelCategorySetting)
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateItems(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}