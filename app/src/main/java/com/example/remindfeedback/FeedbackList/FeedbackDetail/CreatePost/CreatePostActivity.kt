package com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import androidx.appcompat.app.ActionBar
import android.content.Intent
import android.graphics.Bitmap
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost.Record.RecordActivity
import com.example.remindfeedback.R
import com.soundcloud.android.crop.Crop
import kotlinx.android.synthetic.main.activity_create_post.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class CreatePostActivity : AppCompatActivity(), ContractCreatePost.View {

    var return_type: Int = 0
    internal lateinit var presenterCreatePost: PresenterCreatePost
    var feedback_id: Int = -1
    var board_id: Int = -1
    lateinit var tempFile: File //찍은 사진 넣는부분
    lateinit var tempFile1: File //찍은 사진 넣는부분
    lateinit var tempFile2: File //찍은 사진 넣는부분
    lateinit var tempFile3: File //찍은 사진 넣는부분
    private val PICK_FROM_ALBUM = 1
    private val PICK_FROM_CAMERA = 2
    private val PICK_FROM_CAMERA_VIDEO = 3
    private val PICK_FROM_AUDIO = 4
    var lastUri_1: String? = null
    var lastUri_2: String? = null
    var lastUri_3: String? = null
    var arrayList = arrayListOf<Uri?>()
    lateinit var ab: ActionBar
    var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        var intent: Intent = intent
        feedback_id = intent.getIntExtra("feedback_id", -1)

        // 액션바 설정
        ab = this!!.supportActionBar!!
        ab.setTitle("게시글 생성하기")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
//        ab.setDisplayHomeAsUpEnabled(true)

        setData()
        presenterCreatePost = PresenterCreatePost().apply {
            view = this@CreatePostActivity
            mContext = this@CreatePostActivity
        }

        //초기 뷰셋팅
        add_File_View.visibility = View.GONE
        contents_Image.setImageResource(R.drawable.ic_text)

        add_File_View.setOnClickListener() {
            if (return_type == 1) {//사진일경우
                //사진이 선택되어있는경우 앨범인지 카메라인지 선택하는 뷰를 띄움
                presenterCreatePost.picktureDialogViwe()
            }
            /*//녹음 비디오 관련 주석처리
            else if(return_type == 2){//비디오일경우
                imageBrowse()
            }else if(return_type == 3){
                val intent = Intent(this, RecordActivity::class.java)
                startActivityForResult(intent,PICK_FROM_AUDIO)
            }
            */
        }

        /* 비디오 녹음 관련 주석 해놓음
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
        */
        contents_Type_Change_Button.setOnClickListener {
            if (intent.hasExtra("title")) {
                Toast.makeText(this, "컨텐츠 타입 변경이 불가능합니다.", Toast.LENGTH_SHORT).show()
            } else {
                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.dialog_contents_type, null)
                val contents_Photo = dialogView.findViewById<TextView>(R.id.contents_Photo)
                val contents_Text = dialogView.findViewById<TextView>(R.id.contents_Text)

                contents_Photo.setOnClickListener {
                    contents_Photo.setBackgroundResource(R.drawable.under_line_gray)
                    contents_Text.setBackgroundResource(R.drawable.all_line)
                    contents_Image.setImageResource(R.drawable.ic_photo_black)
                    return_type = 1
                    add_File_View.visibility = View.VISIBLE
                }

                contents_Text.setOnClickListener {
                    contents_Photo.setBackgroundResource(R.drawable.all_line)
                    contents_Text.setBackgroundResource(R.drawable.under_line_gray)
                    contents_Image.setImageResource(R.drawable.ic_text)
                    return_type = 0
                    add_File_View.visibility = View.GONE
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
    }

    override fun setData() {
        if (intent.hasExtra("title")) {
            ab.setTitle("게시글 수정하기")
            feedback_id = intent.getIntExtra("feedback_id", -1)
            board_id = intent.getIntExtra("board_id", -1)
            Log.e("setData (feedback_id)", feedback_id.toString())
            Log.e("setData (board_id)", board_id.toString())
            create_Post_Title_Tv.setText(intent.getStringExtra("title"))
            create_Post_Script_Tv.setText(intent.getStringExtra("content"))
            if (intent.getIntExtra("board_category", -1) == 0) {  // 글
                contents_Image.setImageResource(R.drawable.ic_text)
                contents_Type_Change_Button.setText("[ 글 ]")
                contents_Type_Change_Button.isClickable.not()
            } else if (intent.getIntExtra("board_category", -1) == 1) { // 사진
                contents_Image.setImageResource(R.drawable.ic_photo_black)
                contents_Type_Change_Button.setText("[ 사진 ]")
            }
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
        when (item.itemId) {
            R.id.create_Post_Button -> {
                return create_Post_Button()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    //버튼 눌렀을때
    fun create_Post_Button(): Boolean {
        val intent = Intent()
        Log.e("return_type", return_type.toString())
        intent.putExtra("return_type", return_type)
        title = create_Post_Title_Tv.text.toString()
        intent.putExtra("board_title", title)
        intent.putExtra("board_content", create_Post_Script_Tv.text.toString())
        if (feedback_id != -1) {
            intent.putExtra("feedback_id", feedback_id)
        }
        if (board_id != -1) {
            intent.putExtra("board_id", board_id)
        }

        if (return_type == 1) {
            intent.putExtra("file1_uri", lastUri_1.toString())
            intent.putExtra("file2_uri", lastUri_2.toString())
            intent.putExtra("file3_uri", lastUri_3.toString())
        } else if (return_type == 2) {
            intent.putExtra("video_uri", lastUri_1.toString())
        } else if (return_type == 3) {
            intent.putExtra("record_uri", lastUri_1.toString())
        }
        setResult(Activity.RESULT_OK, intent)
        finish()

        return true
    }

    //카메라 꺼내는 부분
    override fun cameraBrowse() {
        file_Uri_Holder.text = ""
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
            val photoUri = FileProvider.getUriForFile(
                this,
                "com.example.remindfeedback.fileprovider",
                tempFile
            )
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(cameraintent, PICK_FROM_CAMERA)
        }
    }

    //앨범 꺼내는부분
    override fun imageBrowse() {
        file_Uri_Holder.text = ""
        val intent = Intent(Intent.ACTION_PICK)

        if (return_type == 1) {//사진일경우 사진앨범을 불러옴
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)//여러장 불러올수 있게하는부분
            startActivityForResult(intent, PICK_FROM_ALBUM)
        } else if (return_type == 2) {//동영상일경우 동영상앨범을 불러옴
            intent.type = MediaStore.Video.Media.CONTENT_TYPE
            startActivityForResult(intent, PICK_FROM_CAMERA_VIDEO)
        }

    }

    private fun cropImage(photoUri: Uri) {//카메라 갤러리에서 가져온 사진을 크롭화면으로 보냄
        //크롭 후 저장할 Uri
        tempFile = presenterCreatePost.createImageFile()
        val savingUri = Uri.fromFile(tempFile)//사진촬여은 tempFile이 만들어져있어 넣어서 저장하면됨
        //하지만 갤러리는 크롭후에 이미지를 저장할 파일이 없기에 위 코드를넣어서 추가로 작성해줘야함
        Log.e("cropimage", tempFile.getAbsolutePath())
        lastUri_1 = tempFile.getAbsolutePath()
        //이 유알아이가 최종적으로 내 프로필이 되는것임
        Log.e("saving", lastUri_1)
        file_Uri_Holder.text = tempFile.name
        Crop.of(photoUri, savingUri).asSquare().start(this)
    }

    private fun nonCropImage(arrayList: ArrayList<Uri?>) {
        if (lastUri_1 == null) {

            tempFile1 = presenterCreatePost.createImageFile()
            val savingUri = Uri.fromFile(tempFile1)
            file_Uri_Holder.text = file_Uri_Holder.text.toString() + "    " + tempFile1.name
            lastUri_1 = tempFile1.getAbsolutePath()
            Crop.of(arrayList[0], savingUri).asSquare().start(this)
        } else if (lastUri_1 !== null && lastUri_2 == null) {
            tempFile2 = presenterCreatePost.createImageFile()
            val savingUri = Uri.fromFile(tempFile2)
            file_Uri_Holder.text = file_Uri_Holder.text.toString() + "    " + tempFile2.name
            Crop.of(arrayList[1], savingUri).asSquare().start(this)
            lastUri_2 = tempFile2.getAbsolutePath()
        } else if (lastUri_1 !== null && lastUri_2 !== null && lastUri_3 == null) {
            tempFile3 = presenterCreatePost.createImageFile()
            val savingUri = Uri.fromFile(tempFile3)
            file_Uri_Holder.text = file_Uri_Holder.text.toString() + "    " + tempFile3.name
            Crop.of(arrayList[2], savingUri).asSquare().start(this)
            lastUri_3 = tempFile3.getAbsolutePath()
        }
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
                if (data == null) {
                }//데이터가 널일때를 대비
                else {
                    if (data.clipData == null) {
                        Toast.makeText(this, "이미지를 다중선택 할 수 없는 기기입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        var clipData: ClipData = data.clipData!!
                        Log.e("clipdata", data.clipData!!.itemCount.toString())
                        if (clipData.itemCount > 3) {
                            Toast.makeText(this, "이미지는 3장까지 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                        } else if (clipData.itemCount == 1) {
                            lastUri_1 = clipData.getItemAt(0).uri.toString()
                            Log.e("lastUri_1", lastUri_1.toString())
                            cropImage(clipData.getItemAt(0).uri)
                        } else if (clipData.itemCount > 1 && clipData.itemCount <= 3) {
                            for (i in 0 until clipData.itemCount) {
                                Log.e("image uri", clipData.getItemAt(i).uri.toString())
                                arrayList.add(clipData.getItemAt(i).uri)
                                nonCropImage(arrayList)
                            }
                        }
                    }
                }
                //val photoUri = data!!.data
                //cropImage(photoUri)
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
                var newfile: File = File(tempFile.absolutePath)
                newfile.createNewFile()
                var out: OutputStream? = null
                out = FileOutputStream(newfile)
                if (rotatedBitmap != null) {
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                } else {
                    Toast.makeText(this, "이미지 처리 오류!", Toast.LENGTH_SHORT).show()
                }
                val photoUri = Uri.fromFile(tempFile)
                cropImage(photoUri)
            }
            PICK_FROM_CAMERA_VIDEO -> {
                //var mVideo = MediaStore.Video.Media.get
                var uri: Uri = data!!.data!!
                var uri_path: String = getPath(uri)
                lastUri_1 = uri_path

            }
            PICK_FROM_AUDIO -> {
                if (data != null) {
                    Log.e("record_uri", data.getStringExtra("recordUri"))
                    lastUri_1 = data.getStringExtra("recordUri")
                } else {
                    Toast.makeText(this, "음성파일 처리 에러", Toast.LENGTH_SHORT).show()
                }

            }
            Crop.REQUEST_CROP -> {
                //setImage()
            }
        }

    }

    override fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }


}
