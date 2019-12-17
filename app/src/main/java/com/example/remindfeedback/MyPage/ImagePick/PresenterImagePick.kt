package com.example.remindfeedback.MyPage.ImagePick

import android.content.Context
import com.example.remindfeedback.MyPage.ContractMyPage

class PresenterImagePick:ContractImagePick.Presenter {
    lateinit override var view: ContractImagePick.View
    lateinit override var mContext: Context
}