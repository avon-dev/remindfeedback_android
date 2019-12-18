package com.example.remindfeedback.MyPage.ImagePick

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.remindfeedback.R
import com.example.remindfeedback.etcProcess.URLtoBitmapTask
import kotlinx.android.synthetic.main.activity_image_pick.*
import kotlinx.android.synthetic.main.activity_my_page.*
import java.io.File
import java.net.URI
import java.net.URL

class ImagePickActivity : AppCompatActivity(), ContractImagePick.View {

    private val FINAL_TAKE_PHOTO = 1
    private val FINAL_CHOOSE_PHOTO = 2
    private var imageUri: Uri? = null
    var fileUri:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_pick)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("프로필 사진 수정")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)
        setData()

        //카메라 눌렀을때
        Imagepick_Camera_Button.setOnClickListener(){
            val outputImage = File(externalCacheDir, "output_image.jpg")
            if(outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if(Build.VERSION.SDK_INT >= 24){
                FileProvider.getUriForFile(this, "com.example.remindfeedback.fileprovider", outputImage)
            } else {
                Uri.fromFile(outputImage)
            }

            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, FINAL_TAKE_PHOTO)

        }
        //앨범 눌렀을때
        Imagepick_Album_Button.setOnClickListener(){
            val checkSelfPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            else{
                openAlbum()
            }
        }

    }

    fun setData(){
        var test_task: URLtoBitmapTask = URLtoBitmapTask()
        test_task = URLtoBitmapTask().apply {
            var intent:Intent = getIntent()
            var imageData:String = intent.getStringExtra("imageData")
            url = URL(imageData)
        }
        var bitmap: Bitmap = test_task.execute().get()
        modify_Profile_ImageView.setImageBitmap(bitmap)
    }

    private fun openAlbum(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, FINAL_CHOOSE_PHOTO)
    }

    //권한 관련
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 ->
                if (grantResults.isNotEmpty() && grantResults.get(0) ==PackageManager.PERMISSION_GRANTED){
                    openAlbum()
                }
                else {
                    Toast.makeText(this, "You deied the permission", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            //카메라로 찍을경우 이미지 uri를 비트맵에 담아서 셋 함
            FINAL_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri))
                    Log.e("imagepick", imageUri.toString())
                    fileUri = imageUri.toString()
                    modify_Profile_ImageView!!.setImageBitmap(bitmap)

                }
            //앨범에서 가져올경우
            FINAL_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                    else{
                        handleImageBeforeKitkat(data)
                    }
                }
        }
    }



    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        if (DocumentsContract.isDocumentUri(this, uri)){
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority){
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = imagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selsetion)


            }
            else if ("com.android.providers.downloads.documents" == uri.authority){
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = imagePath(contentUri, null)
            }
        }
        else if ("content".equals(uri.scheme, ignoreCase = true)){
            imagePath = imagePath(uri, null)
        }
        else if ("file".equals(uri.scheme, ignoreCase = true)){
            imagePath = uri.path
        }
        Log.e("imagepick2",imagePath)
        fileUri = imagePath
        displayImage(imagePath)
    }

    private fun handleImageBeforeKitkat(data: Intent?) {}

    private fun imagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = contentResolver.query(uri, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    //이미지 받아서 보여주는 부분
    private fun displayImage(imagePath: String?){
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            modify_Profile_ImageView?.setImageBitmap(bitmap)
        }
        else {
            Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show()
        }
    }

    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.modify_profileimage_menu, menu)
        return true
    }

    // 타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){
            R.id.modify_Profile_Image_Button -> { return modify_Profile_Image_Button() }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }

    // 적용 버튼 눌렀을 때
    fun modify_Profile_Image_Button(): Boolean {

        val intent = Intent()
            intent.putExtra("fileUri", fileUri)
            setResult(Activity.RESULT_OK, intent)
            finish()


        return true
    }


}
