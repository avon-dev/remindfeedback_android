package com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.remindfeedback.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PresenterCreatePost : ContractCreatePost.Presenter {


    override lateinit var view: ContractCreatePost.View
    override lateinit var mContext: Context

    //퍼미션 응답 처리 코드
    private val multiplePermissionsCode = 100
    //필요한 퍼미션 리스트
    //원하는 퍼미션을 이곳에 추가하면 된다.
    private val requiredPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)

    //앨범인지 사진인지 선택하는 부분
    override fun picktureDialogViwe() {
        var dialog = AlertDialog.Builder(mContext)
        dialog.setTitle("사진 업로드")
        dialog.setIcon(R.mipmap.ic_launcher)

        fun toast_p() {
            if ( ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){
                // 권한이 있을때 앨범 열기
                view.imageBrowse() //앨범
            } else {
                getPermission()
            }
        }

        fun toast_n() {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ) {
                // 권한이 있을때 카메라 열기
                view.cameraBrowse() //카메라
            } else {
                getPermission()
            }
        }

        var dialog_listener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE ->
                        toast_p()
                    DialogInterface.BUTTON_NEGATIVE ->
                        toast_n()
                }
            }
        }

        dialog.setPositiveButton("앨범", dialog_listener)
        dialog.setNegativeButton("카메라", dialog_listener)
        dialog.show()
    }

    override fun createImageFile(): File {
        // 이미지 파일 이름 ( blackJin_{시간}_ )
        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        val imageFileName = "RemindFeedback_" + timeStamp + "_"

        // 이미지가 저장될 폴더 이름 ( RemindFeedback )
        val storageDir =
            File(Environment.getExternalStorageDirectory().toString() + "/RemindFeedback/")
        //폴더 없으면 만들어줌
        if (!storageDir.exists()) {
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

    override fun getPermission() {
        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면...
        if(rejectedPermissionList.isNotEmpty()){
            //권한 요청!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions((mContext as Activity) , rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }


}