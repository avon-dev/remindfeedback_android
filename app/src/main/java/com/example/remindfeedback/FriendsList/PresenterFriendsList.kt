package com.example.remindfeedback.FriendsList

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.remindfeedback.FeedbackList.ContractMain
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

class PresenterFriendsList: ContractFriendsList.Presenter {


    lateinit override var view: ContractFriendsList.View
    lateinit override var context:Context

    override fun loadItems(list: ArrayList<ModelFriendsList>) {

    }


    override fun addItems(email:String, adapterFriendsList: AdapterFriendsList) {
        val assetManager:AssetManager = context.resources.assets
        val inputStream= assetManager.open("eFriendsList.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("friends")

        for (i in 0 until jArray.length()) {
            val obj = jArray.getJSONObject(i)
            if(obj.getString("friendsEmail").equals(email)){
                var modelFriendsList:ModelFriendsList = ModelFriendsList(obj.getString("friendsName"),obj.getString("friendsScript"),obj.getString("friendsProfileImage"))
                adapterFriendsList.addItem(modelFriendsList)
            }
        }
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }



}