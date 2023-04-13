package com.example.sic.Activity.Registry;

import static com.example.sic.Activity.Registry.Register.id;
import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Register_3 extends DefaultActivity implements View.OnClickListener {

    TextView btnContinue, txtTitle;

    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
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
                i = new Intent(Register_3.this, Register_4.class);
                startActivity(i);
                break;
            case R.id.btnBack:
                if (id == 1) {
                    i = new Intent(Register_3.this, Register_1.class);
                    startActivity(i);
                } else if (id == 2) {
                    i = new Intent(Register_3.this, Register_2.class);
                    startActivity(i);

                }
                break;
        }
    }
}