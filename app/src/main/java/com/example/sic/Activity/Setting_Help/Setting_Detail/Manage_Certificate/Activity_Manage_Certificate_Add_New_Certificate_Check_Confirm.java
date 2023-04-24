package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Activity_Manage_Certificate_Add_New_Certificate_Check_Confirm extends DefaultActivity {

    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_add_new_certificate_check_confirm);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            Intent i = new Intent(Activity_Manage_Certificate_Add_New_Certificate_Check_Confirm.this, Activity_Manage_Certificate.class);
            startActivity(i);
        });
    }
}