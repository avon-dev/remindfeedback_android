package com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import androidx.appcompat.app.ActionBar
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remindfeedback.R
import com.example.remindfeedback.etcProcess.TutorialFrame
import com.google.android.material.internal.ContextUtils
import com.rey.material.app.BottomSheetDialog
import com.rey.material.drawable.ThemeDrawable
import com.rey.material.util.ViewUtil
import com.soundcloud.android.crop.Crop
import com.theartofdev.edmodo.cropper.CropImage
import com.werb.pickphotoview.PickPhotoView
import com.werb.pickphotoview.adapter.SpaceItemDecoration
import com.werb.pickphotoview.util.PickConfig
import com.werb.pickphotoview.util.PickUtils
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.activity_cropper.*
import kotlinx.android.synthetic.main.view_find_password_bottomsheet.*
import java.io.*


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
    var reHoldArrayString = arrayListOf<String>()
    lateinit var ab: ActionBar
    var title: String? = null

    private var adapter: AdapterCreatePost? = null


    var tutorialCount:Int = 0
    internal lateinit var preferences: SharedPreferences
    var mBottomSheetDialog: BottomSheetDialog? = null

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
        preferences = getSharedPreferences("USERSIGN", 0)

        //초기 뷰셋팅
        add_File_View.visibility = View.GONE
        contents_Image.setImageResource(R.drawable.ic_text)
        contents_Type_Change_Button.text = "[ 글 ]"

        add_File_Button.setOnClickListener() {
            if (return_type == 1) {//사진일경우
                //사진이 선택되어있는경우 앨범인지 카메라인지 선택하는 뷰를 띄움
                //presenterCreatePost.picktureDialogViwe()
                showBottomSheet()
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
                    contents_Type_Change_Button.setText("[ 사진 ]")
                    return_type = 1
                    add_File_View.visibility = View.VISIBLE
                }

                contents_Text.setOnClickListener {
                    contents_Photo.setBackgroundResource(R.drawable.all_line)
                    contents_Text.setBackgroundResource(R.drawable.under_line_gray)
                    contents_Image.setImageResource(R.drawable.ic_text)
                    contents_Type_Change_Button.setText("[ 글 ]")
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
        val photoList = findViewById<View>(R.id.image_Pick_Recyclerview) as RecyclerView
        val layoutManager = GridLayoutManager(this, 3)
        photoList.layoutManager = layoutManager
        photoList.addItemDecoration(
            SpaceItemDecoration(
                PickUtils.getInstance(this.applicationContext).dp2px(
                    PickConfig.ITEM_SPACE.toFloat()), 3)
        )
        adapter = AdapterCreatePost(this, null)
        photoList.adapter = adapter
        firstRunCheck()
    }

    private fun showBottomSheet() {
        mBottomSheetDialog = BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog)
        val v = LayoutInflater.from(this).inflate(R.layout.view_find_password_bottomsheet, null)
        ViewUtil.setBackground(v, ThemeDrawable(R.drawable.bg_window_light))
        val sheet_Find_Password = v.findViewById<View>(R.id.sheet_Find_Password) as Button
        val sheet_Find_Email = v.findViewById<View>(R.id.sheet_Find_Email) as Button
        val sheet_Find_Cancel = v.findViewById<View>(R.id.sheet_Find_Cancel) as Button
        val bottom_Sheet_Title = v.findViewById<View>(R.id.bottom_Sheet_Title) as TextView
        sheet_Find_Email.setText("앨범 보기(최대 3장)")
        sheet_Find_Password.setText("카메라 열기(1장)")
        bottom_Sheet_Title.setText("사진을 선택하시겠습니까?")

        sheet_Find_Password.setOnClickListener{
            arrayList.clear()
            reHoldArrayString.clear()
            lastUri_1 = null
            lastUri_2 = null
            lastUri_3 = null
            cameraBrowse()
            adapter!!.updateData(reHoldArrayString)
        }
        //사진여러장
        sheet_Find_Email.setOnClickListener {
            arrayList.clear()
            reHoldArrayString.clear()
            lastUri_1 = null
            lastUri_2 = null
            lastUri_3 = null
            adapter!!.updateData(reHoldArrayString)

            PickPhotoView.Builder(this)
                .setPickPhotoSize(9)
                .setHasPhotoSize(7)
                .setAllPhotoSize(10)
                .setShowCamera(false)
                .setSpanCount(3)
                .setLightStatusBar(false)
                .setStatusBarColor(R.color.white)
                .setToolbarColor(R.color.white)
                .setToolbarTextColor(R.color.black)
                .setSelectIconColor(R.color.colorPrimary)
                .start()
            mBottomSheetDialog!!.dismissImmediately()
        }

        sheet_Find_Cancel.setOnClickListener { mBottomSheetDialog!!.dismissImmediately() }
        v.setBackgroundColor(Color.parseColor("#ffffff"))
        //v.setBackgroundResource(R.drawable.all_line)
        sheet_Find_Password.setBackgroundColor(Color.parseColor("#ddeeff"))
        sheet_Find_Email.setBackgroundColor(Color.parseColor("#ddeeff"))
        sheet_Find_Cancel.setBackgroundColor(Color.parseColor("#ddeeff"))

        mBottomSheetDialog!!.contentView(v)
            .show()
    }

    override fun onPause() {
        super.onPause()
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog!!.dismissImmediately()
            mBottomSheetDialog = null
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
        if (create_Post_Title_Tv.text.isEmpty()) {
            Toast.makeText(this, "제목을 작성해 주세요.", Toast.LENGTH_SHORT).show()
        } else if (create_Post_Script_Tv.text.isEmpty()) {
            Toast.makeText(this, "내용을 작성해 주세요.", Toast.LENGTH_SHORT).show()
        } else if(return_type != 0 && lastUri_1 == null){
            Toast.makeText(this, "파일을 선택해 주세요", Toast.LENGTH_LONG).show()
        }else {

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
        }

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
    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("asasdsadd", "requestCode"+requestCode+"resultCode"+resultCode)



        if(requestCode == PICK_FROM_CAMERA){
            Log.e("asd", "Asdasd")
            val bitmap = MediaStore.Images.Media
                .getBitmap(contentResolver, Uri.fromFile(tempFile))
            val ei = ExifInterface(tempFile.absolutePath)
            val orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            var rotatedBitmap: Bitmap? = presenterCreatePost.rotateImage(bitmap, 90.toFloat());
            storeImage(rotatedBitmap!!)
        }
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            Log.e("PICK_PHOTO_DATA", "PICK_PHOTO_DATA")
            arrayList.clear()
            reHoldArrayString.clear()
            val selectPaths = data!!.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT) as java.util.ArrayList<String>
            var reHoldArray:ArrayList<Uri?> = ArrayList()

            for(i in selectPaths){
                Log.e("selectPaths", "content://"+i)
                reHoldArray.add(i.toUri())
                arrayList.add((i).toUri())
            }

            for(i in arrayList){
                //nonCropImage(arrayList)
                val photoUri = FileProvider.getUriForFile(
                    this,
                    "com.example.remindfeedback.fileprovider",
                    File(i.toString())
                )
                Log.e("photoUri", photoUri.toString())
                CropImage.activity(photoUri)
                    .start(this)
                Log.e("arrayList22", i.toString())
            }

        }
        if(requestCode == Crop.REQUEST_CROP){
            Log.e("크롭해쑴", "")
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri = result.uri
                var bitmap: Bitmap? = null
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(ContextUtils.getActivity(this)!!.getContentResolver(), resultUri)
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

    }
    private fun storeImage(image: Bitmap) {
        val pictureFile: File = presenterCreatePost.createImageFile()
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
            if(lastUri_1 == null){
                lastUri_1 = pictureFile.absolutePath
                reHoldArrayString.add(pictureFile.absolutePath)
            }else if(lastUri_2 == null){
                lastUri_2 = pictureFile.absolutePath
                reHoldArrayString.add(pictureFile.absolutePath)
            }else if(lastUri_3 == null){
                lastUri_3 = pictureFile.absolutePath
                reHoldArrayString.add(pictureFile.absolutePath)
            }
            adapter!!.updateData(reHoldArrayString)

            fos.flush();
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("FragmentActivity.TAG", "File not found: " + e.message)
        } catch (e: IOException) {
            Log.e("FragmentActivity.TAG", "Error accessing file: " + e.message)
        }
    }


    override fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    //첫번째인지 체크
    fun firstRunCheck(){
        var isFirst:Boolean = preferences.getBoolean("firstCreateFeedbackActivity", true);
        if(isFirst){
            startTutorial()
        }
    }
    //튜토리얼 진행
    fun startTutorial(){
        when(tutorialCount){
            0 -> {val tframe = TutorialFrame("포스트의 유형 사진과 글이 있습니다. 사진은 3장까지 선택할 수 있습니다.", "포스트 생성 화면", findViewById<View>(R.id.contents_Image), this, { startTutorial()})
                tutorialCount++
                tframe.mTutorial()}
            1 -> {
                preferences.edit().putBoolean("firstCreateFeedbackActivity", false).apply()
            }

        }
    }




}
