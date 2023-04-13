package com.example.sic.Activity.Registry;

import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Register_2 extends DefaultActivity implements View.OnClickListener {

    TextView btnContinue, txtTitle;
    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnContinue:
                i = new Intent(Register_2.this, Register_3.class);
                startActivity(i);
                break;
            case R.id.btnBack:
                i = new Intent(Register_2.this, Register.class);
                startActivity(i);
                break;
        }
    }
}