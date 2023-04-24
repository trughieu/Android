package com.example.sic.Activity.Registry.chip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;

public class registerChip_5 extends AppCompatActivity implements View.OnClickListener {
    FrameLayout btnBack;
    TextView btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chip_5);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnContinue:
                i = new Intent(registerChip_5.this, registerChip_6.class);
                startActivity(i);
                break;
            case R.id.btnBack:
                i = new Intent(registerChip_5.this, registerChip_4.class);
                startActivity(i);
        }
    }
}