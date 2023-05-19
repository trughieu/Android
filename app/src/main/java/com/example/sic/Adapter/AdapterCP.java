package com.example.sic.Adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.R;
import com.example.sic.model.CertificateCP;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class AdapterCP extends RecyclerView.Adapter<AdapterCP.ViewHolder> {
    int row_index;
    boolean isLastPosition = false;
    private List<CertificateCP> certificateCP;
    private TextView txt_select_id;
    private BottomSheetDialog bottomSheetDialog;

    public AdapterCP(List<CertificateCP> certificateCP, TextView txt_select_id, BottomSheetDialog bottomSheetDialog) {
        this.certificateCP = certificateCP;
        this.txt_select_id = txt_select_id;
        this.bottomSheetDialog = bottomSheetDialog;
    }

    @NonNull
    @Override
    public AdapterCP.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_cp, parent, false);
        return new AdapterCP.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCP.ViewHolder holder, int position) {
        CertificateCP certificateCPs = certificateCP.get(position);
        holder.tvCertificateAuthorityName.setText(certificateCPs.getDescription());
        if (holder.getAdapterPosition() == 0) {
            holder.lnstate.setPadding(0, 40, 0, 0);
            holder.title.setVisibility(View.VISIBLE);
        } else if (holder.getAdapterPosition() == certificateCP.size() - 1 && holder.getAdapterPosition() != 0) {
            holder.title.setVisibility(View.GONE);
            holder.img.setVisibility(View.GONE);
        } else if (holder.getAdapterPosition() != 0) {
            holder.title.setVisibility(View.GONE);
            holder.lnstate.setPadding(0, 0, 0, 0);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedData = certificateCPs.getDescription();
                txt_select_id.setText(selectedData);
                bottomSheetDialog.dismiss();
                Log.d("dess", "onClick: "+certificateCPs.getDescription());
                Log.d("name", "onClick: "+certificateCPs.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return certificateCP.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCertificateAuthorityName, title;
        LinearLayout lnstate;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCertificateAuthorityName = itemView.findViewById(R.id.tvCertificateAuthorityName);
            title = itemView.findViewById(R.id.title);
            lnstate = itemView.findViewById(R.id.lnstate);
            img = itemView.findViewById(R.id.img);
        }
    }
}
