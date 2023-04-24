package com.example.sic.Activity.Registry.chip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;

public class registerChip_2 extends AppCompatActivity {

    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chip_2);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(view -> {
            Intent i = new Intent(registerChip_2.this, registerChip_3.class);
            startActivity(i);
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(registerChip_2.this, registerChip_3.class);
                startActivity(intent);

            }
        }, 5000);
    }
}