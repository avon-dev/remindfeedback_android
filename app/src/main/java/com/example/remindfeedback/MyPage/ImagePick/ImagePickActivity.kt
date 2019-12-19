package com.example.remindfeedback.MyPage.ImagePick

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Context
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
import com.soundcloud.android.crop.Crop
import kotlinx.android.synthetic.main.activity_image_pick.*
import kotlinx.android.synthetic.main.activity_my_page.*
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URL

class ImagePickActivity : AppCompatActivity(), ContractImagePick.View {


    internal val PICK_IMAGE_REQUEST = 1
    internal var filePath: String? = null
    lateinit var tempFile:File //찍은 사진 넣는부분
    private val PICK_FROM_ALBUM = 1
    private val PICK_FROM_CAMERA = 2
    var lastUri: String? = ""

    lateinit var presenterImagePick: PresenterImagePick

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_pick)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("프로필 사진 수정")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)
        setData()

        presenterImagePick = PresenterImagePick().apply {
            view = this@ImagePickActivity
            mContext = this@ImagePickActivity
        }

        //카메라 눌렀을때
        Imagepick_Camera_Button.setOnClickListener(){
            cameraBrowse()
        }
        //앨범 눌렀을때
        Imagepick_Album_Button.setOnClickListener(){
            imageBrowse()
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

    //앨범 꺼내는부분
    override fun imageBrowse() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    //카메라 꺼내는 부분
    override fun cameraBrowse() {
        val cameraintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            tempFile = presenterImagePick.createImageFile()
        } catch (e: IOException) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
            e.printStackTrace()
        }
        if (tempFile != null) {
            //val photoUri = Uri.fromFile(tempFile)
            val photoUri = FileProvider.getUriForFile(this, "com.example.remindfeedback.fileprovider", tempFile)

            Log.e("asd", tempFile.toString())
            Log.e("asd", photoUri.toString())

            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(cameraintent, PICK_FROM_CAMERA)
            Log.e("asd", "일단 카메라 인텐트는 넘어감")
        }
    }
    private fun cropImage(photoUri: Uri) {//카메라 갤러리에서 가져온 사진을 크롭화면으로 보냄
        //갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해줍니다.
        tempFile = presenterImagePick.createImageFile()
        //크롭 후 저장할 Uri
        val savingUri = Uri.fromFile(tempFile)//사진촬여은 tempFile이 만들어져있어 넣어서 저장하면됨
        //하지만 갤러리는 크롭후에 이미지를 저장할 파일이 없기에 위 코드를넣어서 추가로 작성해줘야함

        lastUri = tempFile.getAbsolutePath()
        //이 유알아이가 최종적으로 내 프로필이 되는것임
        Log.e("saving",lastUri)
        Crop.of(photoUri, savingUri).asSquare().start(this)
    }
    //권한 요청
    override fun tedPermission() {
        presenterImagePick.tedPermission(this)
    }

    @SuppressLint("MissingSuperCall")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
            if (tempFile != null) {
                if (tempFile!!.exists()) {
                    if (tempFile!!.delete()) {
                        Log.e("Tag", tempFile!!.absolutePath + " 삭제 성공")
                    }
                }
            }
            return
        }
        when (requestCode) {
            PICK_FROM_ALBUM -> {

                val photoUri = data!!.data
                Log.e("픽프롬 앨범", photoUri!!.toString())
                cropImage(photoUri)
            }
            PICK_FROM_CAMERA -> {
                Log.e("asd", "1")
                val photoUri = Uri.fromFile(tempFile)
                Log.e("asd", "2")
                Log.e("픽프롬 카메라", photoUri.toString())

                cropImage(photoUri)
            }
            Crop.REQUEST_CROP -> {
                setImage()
            }
        }

    }


    fun setImage(){
        val options = BitmapFactory.Options()
        val originalBm = BitmapFactory.decodeFile(tempFile!!.getAbsolutePath(), options)
        Log.d("Tag", "setImage : " + tempFile!!.getAbsolutePath())
        val resizedbitmap = Bitmap.createScaledBitmap(originalBm, 650, 650, true) // 이미지 사이즈 조정
        modify_Profile_ImageView.setImageBitmap(resizedbitmap) // 이미지뷰에 조정한 이미지 넣기
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
            intent.putExtra("fileUri", lastUri)
            setResult(Activity.RESULT_OK, intent)
            finish()


        return true
    }


}
