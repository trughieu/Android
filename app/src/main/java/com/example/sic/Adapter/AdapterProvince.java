package com.example.sic.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.R;
import com.example.sic.model.ProvinceItem;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdapterProvince extends RecyclerView.Adapter<AdapterProvince.ViewHolder> {


    ArrayList<ProvinceItem> provinceItems;


    public AdapterProvince(ArrayList<ProvinceItem> provinceItems) {
        this.provinceItems = provinceItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_province, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProvinceItem provinceItem = provinceItems.get(position);
        holder.province.setText(provinceItem.getName());

    }


    @Override
    public int getItemCount() {
        return provinceItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView province;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            province = itemView.findViewById(R.id.idProvince);
        }
    }

}
