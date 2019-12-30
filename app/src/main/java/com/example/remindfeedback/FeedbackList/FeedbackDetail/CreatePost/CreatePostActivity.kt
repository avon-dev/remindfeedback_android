package com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.activity_login.*
import com.example.remindfeedback.FeedbackList.MainActivity
import android.R.attr.button
import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.remindfeedback.FeedbackList.FeedbackDetail.PresenterFeedbackDetail
import kotlinx.android.synthetic.main.activity_create_feedback.*


class CreatePostActivity : AppCompatActivity(), ContractCreatePost.View {

    var return_type:Int = 0
    internal lateinit var presenterCreatePost: PresenterCreatePost
    var feedback_id:Int = -1
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
        Log.e("return_type", return_type.toString())
        Log.e("create_Post_Title_Tv", create_Post_Title_Tv.text.toString())
        Log.e("create_Post_Script_Tv", create_Post_Script_Tv.text.toString())
        val intent = Intent()
        intent.putExtra("return_type", return_type)
        intent.putExtra("board_title",  create_Post_Title_Tv.text.toString())
        intent.putExtra("board_content", create_Post_Script_Tv.text.toString())
        intent.putExtra("feedback_id", feedback_id)
        setResult(Activity.RESULT_OK, intent)
        finish()

        return true
    }

}
