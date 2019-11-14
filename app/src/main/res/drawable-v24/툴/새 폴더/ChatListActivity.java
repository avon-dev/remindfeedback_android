package com.example.wanna_bet70;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatListActivity extends AppCompatActivity {

    ArrayList<ChatList> mArrayList;//임시
    RecyclerView chat_List_Recyclerview;
    ChatListAdapter adapter;
    long now = System.currentTimeMillis();//시간 받아오는 변수
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("aa hh:mm");
    String strDate = sdf.format(date);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chat_List_Recyclerview = findViewById(R.id.chat_List_Recyclerview);
        mArrayList = new ArrayList<>();//임시
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chat_List_Recyclerview.setLayoutManager(layoutManager);
        adapter = new ChatListAdapter(this, mArrayList);
        chat_List_Recyclerview.setAdapter(adapter);


        adapter.addItem(new ChatList( "김xx","내일 피방 ㄱㄱ", strDate, R.drawable.lion));
        adapter.addItem(new ChatList( "차xx","오늘 머함", strDate, R.drawable.ggobuk));
    }
}
