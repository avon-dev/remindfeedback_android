package com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_create_post.*
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.soundcloud.android.crop.Crop
import kotlinx.android.synthetic.main.activity_image_pick.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class CreatePostActivity : AppCompatActivity(), ContractCreatePost.View {

    var return_type:Int = 0
    internal lateinit var presenterCreatePost: PresenterCreatePost
    var feedback_id:Int = -1
    lateinit var tempFile: File //찍은 사진 넣는부분
    private val PICK_FROM_ALBUM = 1
    private val PICK_FROM_CAMERA = 2
    var lastUri: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        var intent:Intent = intent
        feedback_id = intent.getIntExtra("feedback_id",-1)

        presenterCreatePost = PresenterCreatePost().apply {
            view = this@CreatePostActivity
            mContext = this@CreatePostActivity
        }

        //초기 뷰셋팅
        add_File_View.visibility = View.GONE
        contents_Image.setImageResource(R.drawable.ic_text)

        add_File_View.setOnClickListener(){
            if(return_type == 1){
                //사진이 선택되어있는경우 앨범인지 카메라인지 선택하는 뷰를 띄움
                presenterCreatePost.picktureDialogViwe()
            }
        }


        contents_Type_Change_Button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_contents_type, null)
            val contents_Photo = dialogView.findViewById<TextView>(R.id.contents_Photo)
            val contents_Voice = dialogView.findViewById<TextView>(R.id.contents_Voice)
            val contents_Text = dialogView.findViewById<TextView>(R.id.contents_Text)
            val contents_Video = dialogView.findViewById<TextView>(R.id.contents_Video)

            contents_Photo.setOnClickListener {
                contents_Photo.setBackgroundResource(R.drawable.under_line_gray)
                contents_Voice.setBackgroundResource(R.drawable.all_line)
                contents_Text.setBackgroundResource(R.drawable.all_line)
                contents_Video.setBackgroundResource(R.drawable.all_line)
                contents_Image.setImageResource(R.drawable.ic_photo_black)
                return_type = 1
                add_File_View.visibility = View.VISIBLE
            }
            contents_Voice.setOnClickListener {
                contents_Photo.setBackgroundResource(R.drawable.all_line)
                contents_Voice.setBackgroundResource(R.drawable.under_line_gray)
                contents_Text.setBackgroundResource(R.drawable.all_line)
                contents_Video.setBackgroundResource(R.drawable.all_line)
                contents_Image.setImageResource(R.drawable.ic_voice_black)
                return_type = 3
                add_File_View.visibility = View.VISIBLE

            }
            contents_Text.setOnClickListener {
                contents_Photo.setBackgroundResource(R.drawable.all_line)
                contents_Voice.setBackgroundResource(R.drawable.all_line)
                contents_Text.setBackgroundResource(R.drawable.under_line_gray)
                contents_Video.setBackgroundResource(R.drawable.all_line)
                contents_Image.setImageResource(R.drawable.ic_text)
                return_type = 0
                add_File_View.visibility = View.GONE
            }
            contents_Video.setOnClickListener {
                contents_Photo.setBackgroundResource(R.drawable.all_line)
                contents_Voice.setBackgroundResource(R.drawable.all_line)
                contents_Text.setBackgroundResource(R.drawable.all_line)
                contents_Video.setBackgroundResource(R.drawable.under_line_gray)
                contents_Image.setImageResource(R.drawable.ic_video_black)
                return_type = 2
                add_File_View.visibility = View.VISIBLE
            }
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    //mainTv.text = dialogText.text.toString()
                    //mainRb.rating = dialogRatingBar.rating
                    /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */

                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
                }
                .show()
        }
    }

    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create_post_menu, menu)
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){
            R.id.create_Post_Button -> { return create_Post_Button() }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }
    //버튼 눌렀을때
    fun create_Post_Button(): Boolean {
        val intent = Intent()
        Log.e("return_type",return_type.toString())
        intent.putExtra("return_type", return_type)
        intent.putExtra("board_title",  create_Post_Title_Tv.text.toString())
        intent.putExtra("board_content", create_Post_Script_Tv.text.toString())
        intent.putExtra("feedback_id", feedback_id)
        if(return_type == 1){
            intent.putExtra("file1_uri", lastUri.toString())
        }
        setResult(Activity.RESULT_OK, intent)
        finish()

        return true
    }


    //카메라 꺼내는 부분
    override fun cameraBrowse() {
        val cameraintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        tempFile = presenterCreatePost.createImageFile()
        try {

        } catch (e: IOException) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
            e.printStackTrace()
        }
        if (tempFile != null) {
            //val photoUri = Uri.fromFile(tempFile)
            val photoUri = FileProvider.getUriForFile(this, "com.example.remindfeedback.fileprovider", tempFile)
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(cameraintent, PICK_FROM_CAMERA)
        }
    }

    //앨범 꺼내는부분
    override fun imageBrowse() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    private fun cropImage(photoUri: Uri) {//카메라 갤러리에서 가져온 사진을 크롭화면으로 보냄
        //갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해줍니다.
        tempFile = presenterCreatePost.createImageFile()
        //크롭 후 저장할 Uri
        val savingUri = Uri.fromFile(tempFile)//사진촬여은 tempFile이 만들어져있어 넣어서 저장하면됨
        //하지만 갤러리는 크롭후에 이미지를 저장할 파일이 없기에 위 코드를넣어서 추가로 작성해줘야함

        lastUri = tempFile.getAbsolutePath()
        //이 유알아이가 최종적으로 내 프로필이 되는것임
        Log.e("saving",lastUri)
        file_Uri_Holder.text = tempFile.name
        Crop.of(photoUri, savingUri).asSquare().start(this)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingSuperCall")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
            if (tempFile != null) {
                if (tempFile!!.exists()) {
                    if (tempFile!!.delete()) {
                    }
                }
            }
            return
        }
        when (requestCode) {
            PICK_FROM_ALBUM -> {

                val photoUri = data!!.data
                cropImage(photoUri)
            }
            PICK_FROM_CAMERA -> {
                val bitmap = MediaStore.Images.Media
                    .getBitmap(contentResolver, Uri.fromFile(tempFile))
                val ei = ExifInterface(tempFile.absolutePath)
                val orientation = ei.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )
                var rotatedBitmap: Bitmap? = presenterCreatePost.rotateImage(bitmap, 90.toFloat());
                var newfile:File = File(tempFile.absolutePath)
                newfile.createNewFile()
                var out: OutputStream? = null
                out = FileOutputStream(newfile)
                if (rotatedBitmap != null) {
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                }else{
                    Toast.makeText(this, "이미지 처리 오류!", Toast.LENGTH_SHORT).show()
                }
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


}
