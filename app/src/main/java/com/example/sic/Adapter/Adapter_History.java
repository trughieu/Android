package com.example.sic.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.R;
import com.example.sic.modle.History;

import java.util.ArrayList;

public class Adapter_History extends RecyclerView.Adapter<Adapter_History.viewHolder> {

    ArrayList<History> arrayList;
    int row_index;
    Activity activity;

//    public Adapter_History(ArrayList<History> arrayList) {
//        this.arrayList = arrayList;
//    }

    public Adapter_History(Activity activity, ArrayList<History> historyArrayList) {
        this.arrayList = historyArrayList;
        this.activity = activity;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_item_history, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        History history = arrayList.get(position);
        holder.tv_Self.setText(history.getTv_1());
        holder.tv_Certificate.setText(history.getTv_2());
        holder.tv_Date.setText(history.getTv_4());
        holder.tv_State.setText(history.getTv_3());
        holder.date.setImageResource(R.drawable.date_img_white);

        if (holder.tv_State.getText().toString().equalsIgnoreCase("SUCCESSFULLY")) {
            holder.tv_State.setTextColor(Color.parseColor("#00BF13"));
        } else {
            holder.tv_State.setTextColor(Color.parseColor("#F02E00"));
        }

        if (row_index == position) {
            holder.date.setImageResource(R.drawable.date_img_blue);
            holder.background.setBackgroundResource(R.drawable.layout_item_history);
            holder.tv_Certificate.setTextColor(Color.parseColor("#0070F4"));
            holder.tv_Date.setTextColor(Color.parseColor("#0070F4"));
            holder.tv_Self.setTextColor(Color.parseColor("#0070F4"));
            Log.d("row", "onBindViewHolder: " + row_index);
        }

//        else {
//            holder.date.setImageResource(R.drawable.date_img_white);
//            holder.background.setBackgroundResource(R.drawable.layout_item_history_black);
//            holder.tv_Certificate.setTextColor(R.color.white);
//            holder.tv_Date.setTextColor(R.color.white);
//            holder.tv_Self.setTextColor(R.color.white);
//        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_Self, tv_Certificate, tv_State, tv_Date;
        ImageView date;
        LinearLayout background;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Certificate = itemView.findViewById(R.id.tv_Certificate);
            tv_Self = itemView.findViewById(R.id.tv_Selfcare);
            tv_State = itemView.findViewById(R.id.tv_state);
            tv_Date = itemView.findViewById(R.id.tv_date);
            date = itemView.findViewById(R.id.date);
            background = itemView.findViewById(R.id.background);


        }
    }
}
