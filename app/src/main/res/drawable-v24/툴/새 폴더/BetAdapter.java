package com.example.wanna_bet70;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BetAdapter extends RecyclerView.Adapter<BetAdapter.ViewHolder> {
    long now = System.currentTimeMillis();//시간 받아오는 변수

    private ArrayList<Bet> items;
    private Context mContext;

    public void addItem(Bet item) {
        items.add(item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected TextView bet_Date;
        protected TextView bet_People;
        protected TextView bet_Script;
        protected TextView bet_Win;
        protected TextView bet_Reward;

        public ViewHolder(View itemView) {
            super(itemView);


            this.bet_Date = (TextView) itemView.findViewById(R.id.bet_Date);
            this.bet_People = (TextView) itemView.findViewById(R.id.bet_People);
            this.bet_Script = (TextView) itemView.findViewById(R.id.bet_Script);
            this.bet_Win = (TextView) itemView.findViewById(R.id.bet_Win);
            this.bet_Reward = (TextView) itemView.findViewById(R.id.bet_Reward);
            itemView.setOnCreateContextMenuListener(this);


        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 메뉴 추가U

            //MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
           // Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);

        }


        // 4. 캔텍스트 메뉴 클릭시 동작을 설정
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                items.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), items.size());


/*
                switch (item.getItemId()) {
                    case 1001:

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext)
                                .inflate(R.layout.bet_edit_box, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                        final EditText editTextPeople = (EditText) view.findViewById(R.id.edittext_bet_people);
                        final EditText editTextScript = (EditText) view.findViewById(R.id.edittext_bet_script);
                        final EditText editTextWin = (EditText) view.findViewById(R.id.edittext_bet_win);
                        final EditText editTextReward = (EditText) view.findViewById(R.id.edittext_bet_reward);


                        editTextDate.setText(items.get(getAdapterPosition()).getDate());
                        editTextPeople.setText(items.get(getAdapterPosition()).getPeople());
                        editTextScript.setText(items.get(getAdapterPosition()).getScript());
                        editTextWin.setText(items.get(getAdapterPosition()).getWin());
                        editTextReward.setText(items.get(getAdapterPosition()).getReward());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                String strDate = editTextDate.getText().toString();
                                String strPeople = editTextPeople.getText().toString();
                                String strScript = editTextScript.getText().toString();
                                String strWin = editTextWin.getText().toString();
                                String strReward = editTextReward.getText().toString();

                                Bet dict = new Bet(strDate, strPeople, strScript, strWin, strReward);

                                items.set(getAdapterPosition(), dict);
                                notifyItemChanged(getAdapterPosition());

                                dialog.dismiss();


                            }
                        });

                        dialog.show();

                        break;

                    case 1002:

                        items.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), items.size());

                        break;

                }

                */
                return true;
            }
        };


    }

    public BetAdapter(Context context, ArrayList<Bet> list) {
        items = list;
        mContext = context;

    }

    @NonNull
    @Override
    public BetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bet_item, viewGroup, false);

        BetAdapter.ViewHolder viewHolder = new BetAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BetAdapter.ViewHolder viewHolder, int position) {
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");

        viewHolder.bet_Date.setText(sdf.format(date));
        viewHolder.bet_People.setText(items.get(position).getPeople());
        viewHolder.bet_Script.setText(items.get(position).getScript());
        viewHolder.bet_Win.setText(items.get(position).getWin());
        viewHolder.bet_Reward.setText(items.get(position).getReward());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}