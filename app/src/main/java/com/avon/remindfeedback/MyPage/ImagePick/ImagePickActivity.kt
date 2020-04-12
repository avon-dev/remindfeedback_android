package com.avon.remindfeedback.MyPage.ImagePick

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.core.net.toUri
import com.avon.remindfeedback.R
import com.google.android.material.internal.ContextUtils
import com.google.android.material.internal.ContextUtils.getActivity
import com.soundcloud.android.crop.Crop
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.werb.pickphotoview.PickPhotoView
import com.werb.pickphotoview.util.PickConfig
import kotlinx.android.synthetic.main.activity_image_pick.*
import java.io.*
import java.lang.Exception

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
            //imageBrowse()
            PickPhotoView.Builder(this)
                .setPickPhotoSize(1)
                .setClickSelectable(true)
                .setShowCamera(false)
                .setSpanCount(3)
                .setLightStatusBar(false)
                .setStatusBarColor(R.color.white)
                .setToolbarColor(R.color.white)
                .setToolbarTextColor(R.color.black)
                .setSelectIconColor(R.color.colorPrimary)
                .start()
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

        tempFile = presenterImagePick.createImageFile()
        try {

        } catch (e: IOException) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
            e.printStackTrace()
        }
        if (tempFile != null) {
            //val photoUri = Uri.fromFile(tempFile)
            val photoUri = FileProvider.getUriForFile(this, "com.avon.remindfeedback.fileprovider", tempFile)
            Log.e("photoUri", photoUri.toString())

        }

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
            val photoUri = FileProvider.getUriForFile(this, "com.avon.remindfeedback.fileprovider", tempFile)
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
    @SuppressLint("MissingSuperCall", "RestrictedApi")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            PICK_FROM_ALBUM -> {
                try{
                    val photoUri = data!!.data
                    if (photoUri != null) {
                        cropImage(photoUri)
                    }
                }catch (e:Exception){Log.e("PICK_FROM_ALBUM", "마이페이지 앨범오류") }

            }
            PICK_FROM_CAMERA -> {
                try{
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
                }catch (e:Exception){Log.e("PICK_FROM_CAMERA", "마이페이지 카메라오류")}

            }
            Crop.REQUEST_CROP -> {
                setImage()
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                Log.e("sadsadda", "asdsaasaaa")
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri: Uri = result.uri
                    var bitmap: Bitmap? = null
                    try {
                       bitmap = MediaStore.Images.Media.getBitmap(getActivity(this)!!.getContentResolver(), resultUri)
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
                    Log.e("error", error.message)
            }

            }
            PickConfig.PICK_PHOTO_DATA -> {
                Log.e("PICK_PHOTO_DATA", "PICK_PHOTO_DATA")

                try{
                    val selectPaths = data!!.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT) as java.util.ArrayList<String>
                    val photoUri = FileProvider.getUriForFile(
                        this,
                        "com.avon.remindfeedback.fileprovider",
                        File(selectPaths[0].toString())
                    )
                    Log.e("photoUri", photoUri.toString())
                    CropImage.activity(photoUri)
                        .start(this)
                    Log.e("arrayList22", selectPaths[0].toString())
                }catch (e:Exception){
                    Toast.makeText(this, "다시 시도해주세요", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun storeImage(image: Bitmap) {
        val pictureFile: File = presenterImagePick.createImageFile()
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
            lastUri = pictureFile.absolutePath
            modify_Profile_ImageView.setImageURI(lastUri!!.toUri())

            fos.flush();
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("FragmentActivity.TAG", "File not found: " + e.message)
        } catch (e: IOException) {
            Log.e("FragmentActivity.TAG", "Error accessing file: " + e.message)
        }
    }

     fun setImage(){

         try{
             val options = BitmapFactory.Options()
             val originalBm = BitmapFactory.decodeFile(tempFile!!.getAbsolutePath(), options)
             val resizedbitmap = Bitmap.createScaledBitmap(originalBm, 650, 650, true) // 이미지 사이즈 조정
             modify_Profile_ImageView.setImageBitmap(resizedbitmap) // 이미지뷰에 조정한 이미지 넣기
         }catch (e:Exception){Log.e("REQUEST_CROP", "마이페이지 크롭오류")
             finish()
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
            intent.putExtra("fileUri", lastUri)
            setResult(Activity.RESULT_OK, intent)
            finish()


        return true
    }


}
