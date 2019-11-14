package com.example.wanna_bet70;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameAttendDialogAdapter extends RecyclerView.Adapter<GameAttendDialogAdapter.ViewHolder>  {


    private ArrayList<GameAttendBox> items;
    private Context mContext;
    public void addItem(GameAttendBox item) {
        items.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView attend_Box_Text;
        private  GameAttendBox aaaaa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.attend_Box_Text = (TextView) itemView.findViewById(R.id.attend_Box_Text);
        }

        void onBind(GameAttendBox aaaaa) {
            this.aaaaa = aaaaa;
            attend_Box_Text.setText(aaaaa.getBox_Text());

            attend_Box_Text.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String aaa = attend_Box_Text.getText().toString();

            Toast.makeText(mContext, aaa, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext.getApplicationContext(), Game_Attend_Box_Activity.class);
            //Intent intent = new Intent();
            intent.putExtra("add_attend", aaa);
            //mContext.startActivity(intent);
            //인텐트 담는거까지는 된거같은데 이거를 게임인포액티비티에서 받아서 리사이클러뷰에 추가를 못하겠음 ㅜㅠㅜㅠ

            //setReult(RESULT_OK, intent);
        }
    }

    public GameAttendDialogAdapter(Context context, ArrayList<GameAttendBox> list) {
        items = list;
        mContext = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_attend_box_item, viewGroup, false);

        GameAttendDialogAdapter.ViewHolder viewHolder = new GameAttendDialogAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //viewHolder.attend_Box_Text.setText(items.get(i).getBox_Text());
        viewHolder.onBind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
