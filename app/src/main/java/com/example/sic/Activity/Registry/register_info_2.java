package com.example.sic.Activity.Registry;

import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;

public class register_info_2 extends AppCompatActivity implements View.OnClickListener {
    TextView btnContinue, txtTitle;
    FrameLayout btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_info_2);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnContinue:
                i = new Intent(register_info_2.this, register_info_phone_email.class);
                startActivity(i);
                break;
            case R.id.btnBack:
                i = new Intent(register_info_2.this, register_info_1.class);
                startActivity(i);
                break;
        }
    }
}