package com.avon.remindfeedback.CategorySetting.CreateCategory.ColorList

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import com.avon.remindfeedback.R
import kotlinx.android.synthetic.main.activity_color_list.*

class ColorListActivity : AppCompatActivity(), ContractColorList.View {

    lateinit var mAdapter: AdapterColorList
    internal lateinit var presenterColorList: PresenterColorList

    var arrayList = arrayListOf<ModelColorList>(
        ModelColorList("#E51C23"),
        ModelColorList("#FF5722"),
        ModelColorList("#FFFF00"),
        ModelColorList("#259B24"),
        ModelColorList("#18FFFF"),
        ModelColorList("#3F51B5"),
        ModelColorList("#9C27B0"),
        ModelColorList("#FF4081"),
        ModelColorList("#964B00"),
        ModelColorList("#000000")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_color_list)

        presenterColorList = PresenterColorList().apply {
            view = this@ColorListActivity
            context = this@ColorListActivity
        }
        mAdapter = AdapterColorList(this, arrayList, presenterColorList)


        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        color_List_Recyclerview.adapter = mAdapter
        val lm = GridLayoutManager(this, 5)
        color_List_Recyclerview.layoutManager = lm
        color_List_Recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌
    }

    override fun returnColor(color: String) {
        intent.putExtra("color", color)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    override fun refresh() {

    }

}
