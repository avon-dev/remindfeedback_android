package com.example.remindfeedback.CategorySetting.CreateCategory

import android.content.Context
import com.example.remindfeedback.CreateFeedback.ContractCreateFeedback

class PresenterCreateCategory : ContractCreateCategory.Presenter {

    override lateinit var view: ContractCreateCategory.View
    override lateinit var context: Context

}