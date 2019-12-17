package com.example.remindfeedback.MyPage.ImagePick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.remindfeedback.R

class ImagePickActivity : AppCompatActivity(), ContractImagePick.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_pick)
    }
}
