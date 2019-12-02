package com.example.remindfeedback.CreateCategory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.example.remindfeedback.CategorySetting.AdapterCategorySetting
import com.example.remindfeedback.CategorySetting.CategorySettingActivity
import com.example.remindfeedback.CategorySetting.ModelCategorySetting
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_create_category.*

class CreateCategoryActivity : AppCompatActivity() {

    lateinit var category_Color1:Button
    lateinit var category_Color2:Button
    lateinit var category_Color3:Button
    lateinit var category_Color4:Button
    lateinit var category_Color5:Button
    lateinit var category_Color6 :Button
    lateinit var category_Color7:Button
    lateinit var category_Color8 :Button
    lateinit var dialogView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("새로운 주제")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        //색상 리스트
        another_Color_Select.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            dialogView = layoutInflater.inflate(R.layout.dialog_color_picker, null)
            initView()
            category_Color1.setOnClickListener {
                setCheck(1)

            }
            category_Color2.setOnClickListener {
                setCheck(2)

            }
            category_Color3.setOnClickListener {
                setCheck(3)

            }
            category_Color4.setOnClickListener {
                setCheck(4)

            }
            category_Color5.setOnClickListener {
                setCheck(5)

            }
            category_Color6.setOnClickListener {
                setCheck(6)

            }
            category_Color7.setOnClickListener {
                setCheck(7)

            }
            category_Color8.setOnClickListener {
                setCheck(8)

            }
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->

                    // 선택한 색상
//                    Toast.makeText(this,"",Toast.LENGTH_SHORT).show()

                }
                .setNegativeButton("취소") { dialogInterface, i ->
                }
                .show()
        }
    }

    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_category_menu, menu)
        return true
    }

    // 타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){
            R.id.add_Category_Button -> { return add_Category_Button() }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }

    // 주제 추가 완료 버튼 눌렀을 때
    fun add_Category_Button(): Boolean {
        Toast.makeText(this@CreateCategoryActivity, "완료 누름.", Toast.LENGTH_SHORT).show()

        CategorySettingActivity.arrayList_category.add(ModelCategorySetting("",create_Feedback_Title.text.toString()))

        finish()
        return true
    }



    fun initView(){
        category_Color1 = dialogView.findViewById<Button>(R.id.category_Color1)
        category_Color2 = dialogView.findViewById<Button>(R.id.category_Color2)
        category_Color3 = dialogView.findViewById<Button>(R.id.category_Color3)
        category_Color4 = dialogView.findViewById<Button>(R.id.category_Color4)
        category_Color5 = dialogView.findViewById<Button>(R.id.category_Color5)
        category_Color6 = dialogView.findViewById<Button>(R.id.category_Color6)
        category_Color7 = dialogView.findViewById<Button>(R.id.category_Color7)
        category_Color8 = dialogView.findViewById<Button>(R.id.category_Color8)

        category_Color1.setBackgroundResource(R.color.blue)
        category_Color2.setBackgroundResource(R.color.gray)
        category_Color3.setBackgroundResource(R.color.red)
        category_Color4.setBackgroundResource(R.color.black)
        category_Color5.setBackgroundResource(R.color.whiteblue)
        category_Color6.setBackgroundResource(R.color.yellow)
        category_Color7.setBackgroundResource(R.color.whitegrey)
        category_Color8.setBackgroundResource(R.color.darkblue)
    }

    fun setCheck(position:Int){
        category_Color1.text=""
        category_Color2.text=""
        category_Color3.text=""
        category_Color4.text=""
        category_Color5.text=""
        category_Color6.text=""
        category_Color7.text=""
        category_Color8.text=""
        if(position == 1){
            category_Color1.text = "✔"
        }else if(position ==2){
            category_Color2.text = "✔"
        }else if(position ==3){
            category_Color3.text = "✔"
        }else if(position ==4){
            category_Color4.text = "✔"
        }else if(position ==5){
            category_Color5.text = "✔"
        }else if(position ==6){
            category_Color6.text = "✔"
        }else if(position ==7){
            category_Color7.text = "✔"
        }else if(position ==8){
            category_Color8.text = "✔"
        }

    }


}
