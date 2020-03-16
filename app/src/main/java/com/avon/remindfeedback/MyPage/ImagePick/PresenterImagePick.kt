package com.avon.remindfeedback.MyPage.ImagePick

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Environment
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PresenterImagePick:ContractImagePick.Presenter {



    lateinit override var view: ContractImagePick.View
    lateinit override var mContext: Context


    override fun createImageFile(): File {
        // 이미지 파일 이름 ( blackJin_{시간}_ )
        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        val imageFileName = "RemindFeedback_" + timeStamp + "_"

        // 이미지가 저장될 폴더 이름 ( RemindFeedback )
        val storageDir = File(Environment.getExternalStorageDirectory().toString() + "/RemindFeedback/")
        Log.e("성공요", storageDir.toString())

        //폴더 없으면 만들어줌
        if (!storageDir.exists()){
            storageDir.mkdirs()
        }
        // 파일 생성
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        Log.e("Tag", "createImageFile : " + image.absolutePath)
        return image
    }


    //이미지 회전하는 부분
    override fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(90F)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }
}