package com.example.wanna_bet70;

import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Contents> mArrayList;//임시
    RecyclerView contents_recyclerview;
    ContentsAdapter adapter;
    SharedPreferences preferences;
    TextView textView9;
    TextView header_Name;


    private long time= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        preferences  = getSharedPreferences("USERSIGN", MODE_PRIVATE);
        textView9 = (TextView)findViewById(R.id.textView9);


        String bEmail = preferences.getString("user", "");
        Toast.makeText(this, bEmail+"로 로그인 했습니다.", Toast.LENGTH_SHORT).show();

        Toast.makeText(this, preferences.toString(), Toast.LENGTH_SHORT).show();


        final TextView header_Email = (TextView)findViewById(R.id.header_Email);//이거왜 셋텍스트 안됨 ㅜㅠ
       //header_Email.setText(bEmail);


        textView9.setOnClickListener(new View.OnClickListener() {//임시 로그아웃 버튼
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("login", false);
                editor.commit();
                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                NavigationActivity.this.startActivity(intent);
                finish();
            }
        });


        contents_recyclerview = findViewById(R.id.contents_recyclerview);
        mArrayList = new ArrayList<>();//임시

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        contents_recyclerview.setLayoutManager(layoutManager);
        adapter = new ContentsAdapter(this, mArrayList);
        contents_recyclerview.setAdapter(adapter);

        List<String> listName = Arrays.asList("사다리타기", "폭탄넘기기", "악어이빨", "더미 1", "더미 2", "더미 3", "더미 4");





        for (int i = 0; i < listName.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Contents data = new Contents(null);
            data.setName(listName.get(i));
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();












        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }



    }
    /*

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {//가장 중요한 메서드, 드로어의 항목 클릭시 이프문을 타는 구조
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        FragmentManager manager = getFragmentManager();

        if (id == R.id.nav_first_layout) {

            Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_second_layout) {

            Intent intent = new Intent(getApplicationContext(), AccountInfoActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_friends_list) {

            Intent intent = new Intent(getApplicationContext(), FriendsListActivity.class);
            startActivity(intent);
        }else if (id == R.id. nav_messanger) {

            Intent intent = new Intent(getApplicationContext(), ChatingActivity.class);
            startActivity(intent);
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }



}
