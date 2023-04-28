package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Activity_Manage_Certificate_Add_New_Certificate_Check_Detail extends DefaultActivity {

    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_add_new_certificate_check_detail);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}