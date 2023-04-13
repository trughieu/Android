package com.example.sic.Activity.Registry;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Register_12 extends DefaultActivity {

    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register12);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(view -> {
            Intent i = new Intent(Register_12.this, Register_13.class);
            startActivity(i);
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(Register_12.this, Register_13.class);
                startActivity(intent);

            }
        }, 5000);
    }
}