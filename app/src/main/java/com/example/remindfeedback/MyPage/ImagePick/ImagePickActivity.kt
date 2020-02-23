package com.example.remindfeedback.MyPage.ImagePick

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.content.FileProvider
import com.example.remindfeedback.R
import com.example.remindfeedback.etcProcess.URLtoBitmapTask
import com.soundcloud.android.crop.Crop
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_pick.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.URL

class ImagePickActivity : AppCompatActivity(), ContractImagePick.View {


    lateinit var tempFile:File //찍은 사진 넣는부분
    private val PICK_FROM_ALBUM = 1
    private val PICK_FROM_CAMERA = 2
    var lastUri: String? = ""
    var isCamera:Boolean = false

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
            isCamera = true
            cameraBrowse()
        }
        //앨범 눌렀을때
        Imagepick_Album_Button.setOnClickListener(){
            isCamera = false
            imageBrowse()
        }

    }

    fun setData(){
        val intent:Intent = getIntent()
        if(!intent.getStringExtra("imageData").equals("")){

            Picasso.get().load(intent.getStringExtra("imageData")).into(modify_Profile_ImageView);


        }else{
            modify_Profile_ImageView.setImageResource(R.drawable.ic_default_profile)
        }


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
        tempFile = presenterImagePick.createImageFile()
        try {

        } catch (e: IOException) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
            e.printStackTrace()
        }
        if (tempFile != null) {
            //val photoUri = Uri.fromFile(tempFile)
            val photoUri = FileProvider.getUriForFile(this, "com.example.remindfeedback.fileprovider", tempFile)
            Log.e("photoUri", photoUri.toString())
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(cameraintent, PICK_FROM_CAMERA)
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

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingSuperCall")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()

            return
        }
        when (requestCode) {
            PICK_FROM_ALBUM -> {

                val photoUri = data!!.data
                if (photoUri != null) {
                    cropImage(photoUri)
                }
            }
            PICK_FROM_CAMERA -> {
                val bitmap = MediaStore.Images.Media
                    .getBitmap(contentResolver, Uri.fromFile(tempFile))
                val ei = ExifInterface(tempFile.absolutePath)
                val orientation = ei.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )
                var rotatedBitmap: Bitmap? = presenterImagePick.rotateImage(bitmap, 90.toFloat());
                modify_Profile_ImageView.setImageBitmap(rotatedBitmap)
                var newfile:File = File(tempFile.absolutePath)
                newfile.createNewFile()
                var out:OutputStream? = null
                out = FileOutputStream(newfile)
                if (rotatedBitmap != null) {
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                }else{
                    Toast.makeText(this, "이미지 처리 오류!", Toast.LENGTH_SHORT).show()
                }
                Log.e("isCamera", isCamera.toString())
                val photoUri = Uri.fromFile(tempFile)
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
        val resizedbitmap = Bitmap.createScaledBitmap(originalBm, 650, 650, true) // 이미지 사이즈 조정
       // modify_Profile_ImageView.setImageBitmap(resizedbitmap) // 이미지뷰에 조정한 이미지 넣기
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
