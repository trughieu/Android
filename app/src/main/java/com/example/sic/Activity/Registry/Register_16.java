package com.example.sic.Activity.Registry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.Activity.Login.MainActivity;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Register_16 extends DefaultActivity implements View.OnClickListener {
    FrameLayout btnBack;
    TextView btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register16);

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
                i = new Intent(Register_16.this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.btnBack:
                i = new Intent(Register_16.this, Register_15.class);
                startActivity(i);
        }
    }
}