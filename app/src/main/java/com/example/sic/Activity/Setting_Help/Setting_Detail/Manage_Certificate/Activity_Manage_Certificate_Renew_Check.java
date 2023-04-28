package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Activity_Manage_Certificate_Renew_Check extends DefaultActivity implements View.OnClickListener {
    TextView txt_select_id;
    String s;
    TextView conf_E_iden, conf_bio, conf_Pin, btn_Detail;
    FrameLayout btnBack;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_renew_check);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnBack = findViewById(R.id.btnBack);
        btn_Detail = findViewById(R.id.btn_Detail);
        btn_Detail.setOnClickListener(this);
        btnBack.setOnClickListener(this);


        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Activity_Manage_Certificate_Renew_Check.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(
                        R.layout.bottom_authori_confirm_manage_certificate_add_new_and_change_scal_and_renew
                        , findViewById(R.id.bottom_sheet_layout_add_new_certificate));

                conf_bio = bottomSheetView.findViewById(R.id.conf_bio);
                conf_E_iden = bottomSheetView.findViewById(R.id.conf_e_iden);
                conf_Pin = bottomSheetView.findViewById(R.id.conf_pin);

                conf_Pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_Pin.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                conf_E_iden.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_E_iden.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                conf_bio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_bio.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnBack:
                intent = new Intent(Activity_Manage_Certificate_Renew_Check.this, Activity_Manage_Certificate_Renew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
                finish();
                break;
            case R.id.btn_Detail:
                intent = new Intent(Activity_Manage_Certificate_Renew_Check.this, Activity_Manage_Certificate_Renew_Check_Detail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
                finish();
                break;
        }
    }
}