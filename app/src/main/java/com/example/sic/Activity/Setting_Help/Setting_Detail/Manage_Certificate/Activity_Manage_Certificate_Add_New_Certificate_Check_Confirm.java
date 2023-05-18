package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Add_New_Certificate_Check_Confirm extends DefaultActivity {

    FrameLayout btnBack;
    TextView uid_detail, common_detail, o_detail, state_detail, country_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_add_new_certificate_check_confirm);
        uid_detail = findViewById(R.id.uid_detail);
        common_detail = findViewById(R.id.common_detail);
        o_detail = findViewById(R.id.common_detail);
        state_detail = findViewById(R.id.state_detail);
        country_detail = findViewById(R.id.country_detail);
        btnBack = findViewById(R.id.btnBack);

        Response response = (Response) getIntent().getSerializableExtra("response");

        if (response != null) {
            uid_detail.setText(response.getSubject().get("UID").get(0));
            common_detail.setText(response.getSubject().get("CN").get(0));
            o_detail.setText(response.getSubject().get("O").get(0));
            state_detail.setText(response.getSubject().get("ST").get(0));
            country_detail.setText(response.getSubject().get("C").get(0));
        }

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(Activity_Manage_Certificate_Add_New_Certificate_Check_Confirm.this, Activity_Manage_Certificate.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}