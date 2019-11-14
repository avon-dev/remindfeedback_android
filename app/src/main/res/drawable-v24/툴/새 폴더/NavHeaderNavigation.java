package com.example.wanna_bet70;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NavHeaderNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_navigation);

        ImageView bar_Profile = (ImageView)findViewById(R.id.header_Profile_Image);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        bar_Profile.setImageBitmap(bitmap);
    }
}
