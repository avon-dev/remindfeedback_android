package com.example.remindfeedback.FriendsList

import android.content.Context
import com.example.remindfeedback.FeedbackList.ContractMain
import java.util.ArrayList

class PresenterFriendsList: ContractFriendsList.Presenter {


    lateinit override var view: ContractFriendsList.View

    override fun loadItems(list: ArrayList<ModelFriendsList>) {

    }

    override fun addItems(position: Int) {
    }

    override fun removeItems(position: Int, id: Int, context: Context) {
    }

    override fun updateItems(position: Int) {
    }
}