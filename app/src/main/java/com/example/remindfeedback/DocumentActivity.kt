package com.example.remindfeedback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_document.*

class DocumentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document)

        var intent = intent
        Log.e("script", intent.getStringExtra("script"))
        document_Script.setText(intent.getStringExtra("script"))

        script_OK_Button.setOnClickListener{
            finish()
        }
    }
}
