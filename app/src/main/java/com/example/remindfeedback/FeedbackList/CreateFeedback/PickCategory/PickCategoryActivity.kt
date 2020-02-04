package com.example.remindfeedback.FeedbackList.CreateFeedback.PickCategory

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_color_list.*
import kotlinx.android.synthetic.main.activity_pick_category.*

class PickCategoryActivity : AppCompatActivity(), ContractPickCategory.View {


    lateinit var mAdapter: AdapterPickCategory
    internal lateinit var presenterPickCategory: PresenterPickCategory

    var arrayList = arrayListOf<ModelPickCategory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_pick_category)

        presenterPickCategory = PresenterPickCategory().apply {
            view = this@PickCategoryActivity
            context = this@PickCategoryActivity
        }
        var intent: Intent = intent
        if(intent.hasExtra("isMain")){
            arrayList.add(ModelPickCategory(-2, "", "전체보기"))
        }


        mAdapter = AdapterPickCategory(this, arrayList, presenterPickCategory)
        presenterPickCategory.getData(arrayList)

        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        pick_Category_Recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        pick_Category_Recyclerview.layoutManager = lm
        pick_Category_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌
    }

    override fun returnData(modelPickCategory: ModelPickCategory) {
        intent.putExtra("color", modelPickCategory.color)
        intent.putExtra("title", modelPickCategory.title)
        intent.putExtra("id", modelPickCategory.id)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }

}
