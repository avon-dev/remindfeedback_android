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
import petrov.kristiyan.colorpicker.CustomDialog
import android.R.attr.button
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog


class CreatePostActivity : AppCompatActivity(), ContractCreatePost.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        type_Change_Button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_contents_type, null)
            val contents_Photo = dialogView.findViewById<TextView>(R.id.contents_Photo)
            val contents_Voice = dialogView.findViewById<TextView>(R.id.contents_Voice)
            val contents_Text = dialogView.findViewById<TextView>(R.id.contents_Text)
            val contents_Video = dialogView.findViewById<TextView>(R.id.contents_Video)

            contents_Photo.setOnClickListener {
               Log.e("tag", "눌림")
                contents_Photo.setBackgroundResource(R.drawable.under_line_gray)
                contents_Voice.setBackgroundResource(R.drawable.all_line)
                contents_Text.setBackgroundResource(R.drawable.all_line)
                contents_Video.setBackgroundResource(R.drawable.all_line)
                contents_Image.setImageResource(R.drawable.ic_photo_black)
                //finish()
            }
            contents_Voice.setOnClickListener {
                Log.e("tag", "눌림")
                contents_Photo.setBackgroundResource(R.drawable.all_line)
                contents_Voice.setBackgroundResource(R.drawable.under_line_gray)
                contents_Text.setBackgroundResource(R.drawable.all_line)
                contents_Video.setBackgroundResource(R.drawable.all_line)
                contents_Image.setImageResource(R.drawable.ic_voice_black)
                //finish()
            }
            contents_Text.setOnClickListener {
                Log.e("tag", "눌림")
                contents_Photo.setBackgroundResource(R.drawable.all_line)
                contents_Voice.setBackgroundResource(R.drawable.all_line)
                contents_Text.setBackgroundResource(R.drawable.under_line_gray)
                contents_Video.setBackgroundResource(R.drawable.all_line)
                contents_Image.setImageResource(R.drawable.ic_text)
                //finish()
            }
            contents_Video.setOnClickListener {
                Log.e("tag", "눌림")
                contents_Photo.setBackgroundResource(R.drawable.all_line)
                contents_Voice.setBackgroundResource(R.drawable.all_line)
                contents_Text.setBackgroundResource(R.drawable.all_line)
                contents_Video.setBackgroundResource(R.drawable.under_line_gray)
                contents_Image.setImageResource(R.drawable.ic_video_black)
                //finish()
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
        Toast.makeText(this@CreatePostActivity, "완료누름 누름.", Toast.LENGTH_SHORT).show()

        return true
    }

}
