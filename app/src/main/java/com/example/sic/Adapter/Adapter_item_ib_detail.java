package com.example.sic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.R;
import com.example.sic.modle.Ib_detail;

import java.util.ArrayList;

public class Adapter_item_ib_detail extends RecyclerView.Adapter<Adapter_item_ib_detail.viewHolder> {

    ArrayList<Ib_detail> arrayList;

    public Adapter_item_ib_detail(ArrayList<Ib_detail> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ib_detail, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Ib_detail ib_detail = arrayList.get(position);
        holder.tv_perform.setText(ib_detail.getTv_1());
        holder.tv_operating.setText(ib_detail.getTv_2());
        holder.tv_operating_detail.setText(ib_detail.getTv_3());
        holder.tv_ip.setText(ib_detail.getTv_4());
        holder.tv_ip_detail.setText(ib_detail.getTv_5());
        holder.tv_browser.setText(ib_detail.getTv_6());
        holder.tv_browser_detail.setText(ib_detail.getTv_7());
        holder.tv_rp.setText(ib_detail.getTv_8());
        holder.tv_rp_detail.setText(ib_detail.getTv_9());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tv_perform, tv_operating, tv_operating_detail,
                tv_ip, tv_ip_detail, tv_browser, tv_browser_detail, tv_rp, tv_rp_detail;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_perform = itemView.findViewById(R.id.tv_perform);
            tv_operating = itemView.findViewById(R.id.tv_operating);
            tv_operating_detail = itemView.findViewById(R.id.tv_operating_detail);
            tv_ip = itemView.findViewById(R.id.tv_ip);
            tv_ip_detail = itemView.findViewById(R.id.tv_ip_detail);
            tv_browser = itemView.findViewById(R.id.tv_browser);
            tv_browser_detail = itemView.findViewById(R.id.tv_browser_detail);
            tv_rp = itemView.findViewById(R.id.tv_rp);
            tv_rp_detail = itemView.findViewById(R.id.tv_rp_detail);

        }
    }
}
