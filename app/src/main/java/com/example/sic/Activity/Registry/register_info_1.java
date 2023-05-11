package com.example.sic.Activity.Registry;

import static com.example.sic.Activity.Registry.Register.id;
import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.Activity.Registry.chip.registerChip;
import com.example.sic.Activity.Registry.nonChip.registerNonChip;
import com.example.sic.AppData;
import com.example.sic.Dev_activity;
import com.example.sic.R;

public class register_info_1 extends Dev_activity implements View.OnClickListener {

    TextView btnContinue, txtTitle, btnDisagree;

    FrameLayout btnBack;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_info_1);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppData.getInstance().getAppTitle());

        btnDisagree = findViewById(R.id.btnDisagree);
        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnDisagree.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnContinue:
                intent = new Intent(register_info_1.this, register_info_2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                
                startActivity(intent);
                finish();
                break;
            case R.id.btnBack:
            case R.id.btnDisagree:
                if (id == 1) {
                    intent = new Intent(register_info_1.this, registerNonChip.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                finish();
                } else if (id == 2) {
                    intent = new Intent(register_info_1.this, registerChip.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                finish();
                }
                break;
        }
    }
}