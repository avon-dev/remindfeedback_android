package com.example.wanna_bet70;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private long time= 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView myPage = (ImageView) findViewById(R.id.myPage);//마이페이지 버튼누르면 다음화면으로

        myPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                //intent.putExtra("nameText", name);
                startActivity(intent);
            }
        });



        Button Ladder_Button = (Button) findViewById(R.id.Ladder_Button);//마이페이지 버튼누르면 다음화면으로

        Ladder_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameInfoActivity.class);
                //intent.putExtra("nameText", name);
                startActivity(intent);
            }
        });

        Button navi = (Button) findViewById(R.id.navi);//마이페이지 버튼누르면 다음화면으로

        navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
            }
        });


        ImageView messanger_Button = (ImageView) findViewById(R.id.messanger_Button);//마이페이지 버튼누르면 다음화면으로

        messanger_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(getApplicationContext(), FriendsListActivity.class);
               // Intent intent = new Intent(getApplicationContext(), Friends_List_ListActivity.class);
                //startActivity(intent);
            }
        });

    }


    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }



    }


}
