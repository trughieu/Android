package com.example.sic.Activity.Setting_Help;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;

public class TermDetail extends AppCompatActivity {
    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(TermDetail.this, SettingHelp.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}