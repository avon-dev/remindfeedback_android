package com.example.remindfeedback.CreateCategory

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.remindfeedback.FriendsList.FindFriends.FindFriendsActivity
import com.example.remindfeedback.R
import kotlinx.android.synthetic.main.activity_create_category.*
import petrov.kristiyan.colorpicker.ColorPicker
import java.util.ArrayList

class CreateCategoryActivity : AppCompatActivity() {

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
            val colorPicker = ColorPicker(this@CreateCategoryActivity)
            val colors = ArrayList<String>()
            colors.add("#82B926")
            colors.add("#a276eb")
            colors.add("#6a3ab2")
            colors.add("#666666")
            colors.add("#FFFF00")
            colors.add("#3C8D2F")
            colors.add("#FA9F00")
            colors.add("#FF0000")

            colorPicker
                .setDefaultColorButton(Color.parseColor("#f6f6f6"))
                .setColors(colors)
                .setColumns(5)
                .setRoundColorButton(true)
                .setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
                    override fun onChooseColor(position: Int, color: Int) {
                        Toast.makeText(this@CreateCategoryActivity, "position : "+position, Toast.LENGTH_SHORT).show()                    }

                    override fun onCancel() {

                    }
                })
                .addListenerButton(
                    "newButton"
                ) { v, position, color -> Log.d("position", "" + position) }.show()
        }


    }



}
