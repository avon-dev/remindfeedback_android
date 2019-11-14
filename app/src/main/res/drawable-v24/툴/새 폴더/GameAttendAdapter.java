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

import java.util.ArrayList;

public class GameAttendAdapter extends RecyclerView.Adapter<GameAttendAdapter.ViewHolder> {

    private ArrayList<GameAttend> items;
    private Context mContext;

    public void addItem(GameAttend item) {
        items.add(item);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{

        protected TextView attend_Text;

        public ViewHolder(View itemView) {
            super(itemView);


            this.attend_Text = (TextView) itemView.findViewById(R.id.attend_Text);
            itemView.setOnCreateContextMenuListener(this);


        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            //Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                items.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), items.size());


                return true;
            }
        };




    }


    public GameAttendAdapter(Context context, ArrayList<GameAttend> list) {
        items = list;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_attend_item, viewGroup, false);

        GameAttendAdapter.ViewHolder viewHolder = new GameAttendAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.attend_Text.setText(items.get(i).getAttend());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
