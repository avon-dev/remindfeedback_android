package com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import android.content.Context
import android.graphics.Bitmap
import com.example.remindfeedback.ServerModel.CreateBoardText
import java.io.File

interface ContractCreatePost {
    interface View{
        fun cameraBrowse()
        fun imageBrowse()
    }

    interface Presenter {
        var view: View
        var mContext:Context
        fun picktureDialogViwe()
        fun createImageFile(): File
        fun rotateImage(source: Bitmap, angle: Float): Bitmap

    }

}