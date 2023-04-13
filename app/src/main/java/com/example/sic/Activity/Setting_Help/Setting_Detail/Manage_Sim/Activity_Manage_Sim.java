package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Sim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.Activity.Setting_Help.Setting_Detail.Activity_Setting_Detail;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Activity_Manage_Sim extends DefaultActivity implements View.OnClickListener {
    TextView btn_Bind_New_Sim;
    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sim);
        btnBack = findViewById(R.id.btnBack);
        btn_Bind_New_Sim = findViewById(R.id.tv_Bind_New_Sim);
        btn_Bind_New_Sim.setOnClickListener(this);
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.tv_Bind_New_Sim:
                i = new Intent(Activity_Manage_Sim.this, Activity_Manage_Sim_Bind_New_Sim.class);
                startActivity(i);
                break;
            case R.id.btnBack:
                i = new Intent(Activity_Manage_Sim.this, Activity_Setting_Detail.class);
                startActivity(i);
                break;
        }
    }
}