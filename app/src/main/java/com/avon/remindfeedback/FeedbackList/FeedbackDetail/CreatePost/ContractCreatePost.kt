package com.avon.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface ContractCreatePost {
    interface View {
        fun cameraBrowse()
        fun imageBrowse()
        fun getPath(uri: Uri): String

        fun setData()

    }

    interface Presenter {
        var view: View
        var mContext: Context
        fun getPermission()
        fun picktureDialogViwe()
        fun createImageFile(): File
        fun rotateImage(source: Bitmap, angle: Float): Bitmap

    }

}