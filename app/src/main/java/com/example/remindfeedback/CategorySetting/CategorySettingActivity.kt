package com.example.remindfeedback.CategorySetting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.CreateCategory.CreateCategoryActivity
import com.example.remindfeedback.FeedbackList.FeedbackDetail.AdapterFeedbackDetail
import com.example.remindfeedback.FeedbackList.FeedbackDetail.ModelFeedbackDetail
import com.example.remindfeedback.FeedbackList.FeedbackDetail.PresenterFeedbackDetail
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import kotlinx.android.synthetic.main.activity_category_setting.*
import kotlinx.android.synthetic.main.activity_feedback_detail.*
import kotlinx.android.synthetic.main.activity_login.*

class CategorySettingActivity : AppCompatActivity() , ContextCategorySetting.View{
    private val TAG = "PresenterCategorySetting"
    internal lateinit var presenterCategorySetting: PresenterCategorySetting
    var arrayList = arrayListOf<ModelCategorySetting>(
        ModelCategorySetting("red", "첫번째 주제"),
        ModelCategorySetting("blue", "첫번째 주제"),
        ModelCategorySetting("black", "첫번째 주제")


    )
    val mAdapter = AdapterCategorySetting(this, arrayList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_setting)

        //액션바 설정
        var ab: ActionBar = this!!.supportActionBar!!
        ab.setTitle("주제 설정")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        category_Setting_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        category_Setting_Recyclerview.layoutManager = lm
        category_Setting_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        presenterCategorySetting = PresenterCategorySetting().apply {
            view = this@CategorySettingActivity
        }
        //presenterFeedbackDetail.loadItems(arrayList)




    }



    //타이틀바에 어떤 menu를 적용할지 정하는부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create_category_menu, menu)
        return true
    }

    //타이틀바 메뉴를 클릭했을시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){
            R.id.create_category_Button -> { return create_category_Button() }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }
    //찾기버튼 눌렀을때
    fun create_category_Button(): Boolean {
        val intent = Intent(this, CreateCategoryActivity::class.java)
        startActivity(intent)
        return true
    }

    override fun refresh() {

    }
}
