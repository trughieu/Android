package com.example.sic.Activity.Home.Inbox;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Inbox_detail_detail_1_1 extends DefaultActivity {

    LinearLayout btn_Close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail_detail11);
        btn_Close = findViewById(R.id.btn_Close);
        btn_Close.setOnClickListener(view -> {
            finish();
        });
    }
}