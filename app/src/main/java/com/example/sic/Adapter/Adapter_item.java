package com.example.sic.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.sic.Activity.Home.Inbox.Inbox_detail;
import com.example.sic.Activity.Home.Inbox.Inbox_detail_1;
import com.example.sic.R;
import com.example.sic.modle.Message;

import java.util.ArrayList;

public class Adapter_item extends RecyclerView.Adapter<Adapter_item.viewHolder> {

    ArrayList<Message> arrayList;
    Context context;
    int row_index;

    public Adapter_item(ArrayList<Message> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public Adapter_item(ArrayList<Message> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Message message = arrayList.get(position);
        holder.tv_signing_tester.setText(message.getMessageCaption());
        holder.tv_submit.setText(context.getResources().getString(R.string.orders_prefix_issued_by) + " " + message.getScaIdentity());
        holder.tv_date.setText(message.getCreatedDt());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, Inbox_detail.class);
            intent.putExtra("transactionId", message.getTransactionId());
            context.startActivity(intent);
        });
        if (row_index == position) {
            holder.background.setBackgroundResource(R.drawable.layout_item);
            holder.tv_signing_tester.setTextColor(Color.parseColor("#0070F4"));
            holder.tv_date.setTextColor(Color.parseColor("#0070F4"));
            holder.tv_submit.setTextColor(Color.parseColor("#0070F4"));
            holder.date.setImageResource(R.drawable.date_img_blue);
            Log.d("row", "onBindViewHolder: " + row_index);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tv_signing_tester, tv_submit, tv_date;
        ImageView date;
        LinearLayout background;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_signing_tester = itemView.findViewById(R.id.tv_signing);
            tv_submit = itemView.findViewById(R.id.tv_submit);
            tv_date = itemView.findViewById(R.id.tv_date);
            date = itemView.findViewById(R.id.date);
            background = itemView.findViewById(R.id.background);
        }
    }
}
