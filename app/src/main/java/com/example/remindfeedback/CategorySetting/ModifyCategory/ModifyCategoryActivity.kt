package com.example.remindfeedback.CategorySetting.ModifyCategory

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.remindfeedback.CategorySetting.CreateCategory.ColorList.ColorListActivity
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_create_category.*

class ModifyCategoryActivity: AppCompatActivity(), ContractModifyCategory.View {

    internal lateinit var presenterModifyCategory: PresenterModifyCategory
    var chooseColor :String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)

        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("주제 수정")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        presenterModifyCategory = PresenterModifyCategory().apply {
            view = this@ModifyCategoryActivity
            context = this@ModifyCategoryActivity
        }

        //색상 리스트
        another_Color_Select.setOnClickListener {
            val intent = Intent(this, ColorListActivity::class.java)
            startActivityForResult(intent, 101)
        }

        setData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            101 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> if (data != null) {
                        selected_Color.setBackgroundColor(Color.parseColor(data.getStringExtra("color")))
                        chooseColor = data.getStringExtra("color")
                    }
                    Activity.RESULT_CANCELED -> Toast.makeText(this@ModifyCategoryActivity, "취소됨.", Toast.LENGTH_SHORT).show()

                }

            }
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
            R.id.add_Category_Button -> {
                return add_Category_Button()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    // 주제 수정 완료 버튼 눌렀을 때
    fun add_Category_Button(): Boolean {
        Toast.makeText(this@ModifyCategoryActivity, "완료 누름.", Toast.LENGTH_SHORT).show()
        val intent = Intent()

        if(chooseColor.equals("")){
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }else{
            val id = intent.getIntExtra("id",1)

            intent.putExtra("id",id)
            intent.putExtra("title", create_Category_Title.text.toString())
            intent.putExtra("color", chooseColor)

            presenterModifyCategory.updateItems(id , chooseColor.toString() ,create_Category_Title.text.toString())

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        return true
    }

    // 기존의 데이터를 editText에 넣는 코드
    override fun setData() {
        if(intent.hasExtra("title")){
            val title = intent.getStringExtra("title")
            val color = intent.getStringExtra("color")

            Log.i("주제 제목 getStringExtra", title)
            Log.i("주제 색상 getStringExtra", color)
            create_Category_Title.setText(title)
            selected_Color.setBackgroundColor(Color.parseColor(color))

        }else{
            Log.i("넘어온 인텐트값 없음", "ㅠ")
        }


    }





}

