package com.example.sic.Adapter;

import android.content.Context;
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
import com.example.sic.model.Order;

import java.util.ArrayList;

public class Adapter_item_Manage_Order extends RecyclerView.Adapter<Adapter_item_Manage_Order.viewHolder> {

    Context context;
    ArrayList<Order> orders;
    int row_index;

    public Adapter_item_Manage_Order(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_manage_order, parent, false);
        return new Adapter_item_Manage_Order.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tv_certificate.setText(order.getTv_1());
        holder.tv_certificate_detail.setText(order.getTv_2());
        holder.tv_price.setText(order.getTv_3());

        if (row_index == position) {
            holder.background.setBackgroundResource(R.drawable.layout_item_history);
            holder.tv_certificate.setTextColor(Color.parseColor("#0070F4"));
            holder.tv_price.setTextColor(Color.parseColor("#0070F4"));
            holder.tv_certificate_detail.setTextColor(Color.parseColor("#0070F4"));
            holder.option.setImageResource(R.drawable.union_blue);
            Log.d("row", "onBindViewHolder: " + row_index);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tv_certificate, tv_certificate_detail, tv_price;
        ImageView option;
        LinearLayout background;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_certificate = itemView.findViewById(R.id.tv_certificate);
            tv_certificate_detail = itemView.findViewById(R.id.tv_certificate_detail);
            tv_price = itemView.findViewById(R.id.tv_certificate_price);
            option = itemView.findViewById(R.id.option);
            background = itemView.findViewById(R.id.background);
        }
    }
}
