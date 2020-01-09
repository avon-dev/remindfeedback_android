package com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.remindfeedback.R
import com.example.remindfeedback.ServerModel.CreateBoardText
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PresenterCreatePost:ContractCreatePost.Presenter {



    override lateinit var view: ContractCreatePost.View
    override lateinit var mContext: Context

    //앨범인지 사진인지 선택하는 부분
    override fun picktureDialogViwe() {
        var dialog = AlertDialog.Builder(mContext)
        dialog.setTitle("사진 업로드")
        dialog.setIcon(R.mipmap.ic_launcher)

        fun toast_p() {
            view.imageBrowse() //앨범
        }
        fun toast_n(){
            view.cameraBrowse() //카메라
        }

        var dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        toast_p()
                    DialogInterface.BUTTON_NEGATIVE ->
                        toast_n()
                }
            }
        }

        dialog.setPositiveButton("앨범",dialog_listener)
        dialog.setNegativeButton("카메라",dialog_listener)
        dialog.show()
    }

    override fun createImageFile(): File {
        // 이미지 파일 이름 ( blackJin_{시간}_ )
        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        val imageFileName = "RemindFeedback_" + timeStamp + "_"

        // 이미지가 저장될 폴더 이름 ( RemindFeedback )
        val storageDir = File(Environment.getExternalStorageDirectory().toString() + "/RemindFeedback/")
        //폴더 없으면 만들어줌
        if (!storageDir.exists()){
            storageDir.mkdirs()
        }
        // 파일 생성
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        Log.e("Tag", "createImageFile : " + image.absolutePath)
        return image
    }
    override fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(90F)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }
}