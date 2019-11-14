package com.example.wanna_bet70;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameInfoActivity extends AppCompatActivity {
    ArrayList<GameAttend> mArrayList;//임시
    RecyclerView game_Info_Recyclerview;
    GameAttendAdapter adapter;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1010:
                if(resultCode == RESULT_OK){
                    String add_attend = data.getStringExtra("add_attend");
                    GameAttend dict = new GameAttend(add_attend);
                    mArrayList.add(dict);
                    adapter.notifyDataSetChanged();
                   // Toast.makeText(this, "코드맞았음", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(this, "실행은 됐는데 코드 안맞음!", Toast.LENGTH_SHORT).show();
                }

                break;

        }



    }


    protected  void onRestart() {

        super.onRestart();
        Intent intent = getIntent();
        String myData = intent.getStringExtra("add_attend");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        game_Info_Recyclerview = findViewById(R.id.game_Info_Recyclerview);
        mArrayList = new ArrayList<>();//임시
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        game_Info_Recyclerview.setLayoutManager(layoutManager);
        adapter = new GameAttendAdapter(this, mArrayList);
        game_Info_Recyclerview.setAdapter(adapter);



        final EditText game_Script = (EditText) findViewById(R.id.game_Script);
        final EditText game_Reward = (EditText) findViewById(R.id.game_Reward);


        /*
        Button attent_Plus_Button = (Button)findViewById(R.id.attent_Plus_Button);
        attent_Plus_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GameInfoActivity.this);
                View view = LayoutInflater.from(GameInfoActivity.this)
                        .inflate(R.layout.game_attend_box_item, null, false);
                builder.setView(view);

                //final TextView attend_Text = (TextView) view.findViewById(R.id.attend_Text);
                final AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
*/



        Button attent_Plus_Button = (Button)findViewById(R.id.attent_Plus_Button);
        attent_Plus_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Game_Attend_Box_Activity.class);


/*
                Intent intent2 = getIntent();
                String s = intent2.getStringExtra("add_attend");
                GameAttend dict = new GameAttend(s);
                mArrayList.add(dict);
                adapter.notifyDataSetChanged();
*/
               // intent.putExtra("add_attend", 11);

               // startActivity(intent);
                startActivityForResult(intent, 1010);



            }
        });







        Button game_Start_Button = (Button) findViewById(R.id.game_Start_Button);//게임시작 버튼 누르면 알림창 뜨게끔

        game_Start_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(GameInfoActivity.this);
                    builder.setMessage("정말로 시작하시겠습니까?")

                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    Bundle extras = getIntent().getExtras();
                                    int s = extras.getInt("gameintent");

                                    if(s == 1){
                                        //문자열 전송해야하는 부분
                                        String script = game_Script.getText().toString();
                                        String reward = game_Reward.getText().toString();
                                        Intent intent = new Intent(getApplicationContext(), LadderGameActivity.class);
                                        intent.putExtra("game_Script", script);
                                        intent.putExtra("game_Reward", reward);
                                        startActivity(intent);
                                    }
                                    else if(s == 2){
                                        //Intent intent = new Intent(getApplicationContext(), bombGameActivity.class);
                                        Intent intent = new Intent(getApplicationContext(), GameTestActivity.class);
                                        startActivity(intent);
                                    }
                                    else if(s == 3){
                                        Intent intent = new Intent(getApplicationContext(), CrocoActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(GameInfoActivity.this, "에러!", Toast.LENGTH_SHORT).show();
                                    }



                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    dialog.cancel();// 노 누르면 다이얼로그가 꺼짐
                                }
                            });



                    AlertDialog alert = builder.create();
                    alert.setTitle("게임정보 입력");
                    alert.show();



            }
        });


        adapter.addItem(new GameAttend("김민수"));
        adapter.addItem(new GameAttend("김하늘"));
        adapter.addItem(new GameAttend("홍길동"));




    }

/*
    protected void  onPause() {

        super.onPause();



        Bundle extras = getIntent().getExtras();
        String s = extras.getString("add_attend");
        GameAttend dict = new GameAttend(s);
        mArrayList.add(dict); //마지막 줄에 삽입
        adapter.notifyDataSetChanged();

    }

*/




}

/*
    Intent intent = getIntent();
    String s = intent.getStringExtra("add_attend");
    GameAttend dict = new GameAttend(s);
        mArrayList.add(dict);
                adapter.notifyDataSetChanged();

*/