package com.example.wanna_bet70;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatingActivity extends AppCompatActivity {

    ArrayList<ChatMe> MeList;
    RecyclerView chat_recyclerview;
    ChatAdapter adapter;
    boolean check = false;//채팅치는 입장 바뀌엇는지



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chating);

        chat_recyclerview = findViewById(R.id.chat_recyclerview);
        MeList = new ArrayList<>();

        //리사이클러뷰 리니어로 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chat_recyclerview.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(this, MeList);
        chat_recyclerview.setAdapter(adapter);


        ImageButton change_Button = (ImageButton)findViewById(R.id.change_Button);
        change_Button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(ChatingActivity.this, "주체 바꿈", Toast.LENGTH_SHORT).show();

                if(check){
                    check = false;
                }else{
                    check = true;
                }
            }
        });


        //채팅입력하고 보내기버튼을 눌렀을 시
        Button chat_Send_Button = (Button)findViewById(R.id.chat_Send_Button);
        chat_Send_Button.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {



                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("aa hh:mm");

                String getTime = sdf.format(date);


                //내 채팅과 내가 채팅친 시간을 변수에 넣음
                EditText editChat = (EditText) findViewById(R.id.chating_Text);
                String strName = editChat.getText().toString();
                String strTime = getTime;

                if(strName.equals("")){//아무것도 입력안했을땐 버튼눌러도 아무일 없음

                }else{//채팅 란 안에 내용이 있을 시

                    ChatMe dict;
                    if(check){
                        dict = new ChatMe(strName, strTime, 1);
                    }
                    else{
                        dict = new ChatMe(strName, strTime, 2);
                    }

                    // LinearLayout aa = (LinearLayout)findViewById(R.id.you_Chat_Layout);
                    //aa.setVisibility(View.INVISIBLE);
                    MeList.add(dict); //마지막 줄에 삽입
                    adapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영, 여기에서 에러뜸
                    editChat.setText("");//채팅 입력창 다시 빈칸으로 넣어줌
                    chat_recyclerview.scrollToPosition(MeList.size()-1);//스크롤 맨 마지막으로 내리기

                }


            }
        });

        adapter.addItem(new ChatMe("dd", "dsadsa",1));
        adapter.addItem(new ChatMe("안녕", "xxx", 2));


        //  adapter.addItem(new ChatMe("김민수", "xxx@naver.com",UserType.SELF));
        //  adapter.addItem(new ChatMe("김민수", "xxx@naver.com",UserType.OTHER));

    }




}
