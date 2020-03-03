package com.example.remindfeedback

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.Images
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils.getActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_cropper.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CropperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cropper)
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this);
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri = result.uri
                cropImageView.setImageUriAsync(resultUri)
                var bitmap: Bitmap? = null
                try {
                    bitmap = Images.Media.getBitmap(getActivity(this)!!.getContentResolver(), resultUri)
                } catch (e: FileNotFoundException) { // TODO Auto-generated catch block
                    e.printStackTrace()
                } catch (e: IOException) { // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                storeImage(bitmap!!)

                Log.e("resultUri", resultUri.toString())
                Log.e("resultUri.path", resultUri.path)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }



    fun createImageFile(): File {
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

    private fun storeImage(image: Bitmap) {
        val pictureFile: File = createImageFile()
        if (pictureFile == null) {
            Log.d(
                "FragmentActivity.TAG",
                "Error creating media file, check storage permissions: "
            ) // e.getMessage());
            return
        }
        try {
            val fos = FileOutputStream(pictureFile)
            image.compress(Bitmap.CompressFormat.PNG, 90, fos)
            Log.e("pictureFile", pictureFile.absolutePath)
            fos.flush();
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("FragmentActivity.TAG", "File not found: " + e.message)
        } catch (e: IOException) {
            Log.e("FragmentActivity.TAG", "Error accessing file: " + e.message)
        }
    }


}
