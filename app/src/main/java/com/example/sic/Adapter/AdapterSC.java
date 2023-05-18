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
import com.example.sic.model.CertificateSC;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class AdapterSC extends RecyclerView.Adapter<AdapterSC.ViewHolder> {
    int row_index;
    boolean isLastPosition = false;
    private List<CertificateSC> certificateSC;
    private TextView txt_select_id;
    private BottomSheetDialog bottomSheetDialog;

    public AdapterSC(List<CertificateSC> certificateSC, TextView txt_select_id) {
        this.certificateSC = certificateSC;
        this.txt_select_id = txt_select_id;
    }

    public AdapterSC(List<CertificateSC> certificateSC, TextView txt_select_id, BottomSheetDialog bottomSheetDialog) {
        this.certificateSC = certificateSC;
        this.txt_select_id = txt_select_id;
        this.bottomSheetDialog = bottomSheetDialog;
    }

    @NonNull
    @Override
    public AdapterSC.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_sc, parent, false);
        return new AdapterSC.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSC.ViewHolder holder, int position) {
        CertificateSC certificateS = certificateSC.get(position);
        holder.tvCertificateAuthorityName.setText(certificateS.getName());
        if (holder.getAdapterPosition() == 0) {
            holder.lnstate.setPadding(0, 40, 0, 0);
            holder.title.setVisibility(View.VISIBLE);
        } else if (position == certificateSC.size() - 1) {
            holder.img.setVisibility(View.GONE);
        } else if (holder.getAdapterPosition() != 0) {
            holder.title.setVisibility(View.GONE);
            holder.lnstate.setPadding(0, 0, 0, 0);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedData = certificateS.getName();
                txt_select_id.setText(selectedData);
                Log.d("posistion", "onClick: " + holder.getAdapterPosition());

                bottomSheetDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return certificateSC.size();
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
