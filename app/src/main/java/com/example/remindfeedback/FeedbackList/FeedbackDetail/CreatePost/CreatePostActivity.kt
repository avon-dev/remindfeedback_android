package com.example.remindfeedback.FeedbackList.FeedbackDetail.CreatePost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.remindfeedback.R

class CreatePostActivity : AppCompatActivity(), ContractCreatePost.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
    }
}
